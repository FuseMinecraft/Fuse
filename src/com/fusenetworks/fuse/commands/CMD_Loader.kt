package com.fusenetworks.fuse.commands

import java.io.IOException
import java.security.CodeSource
import java.util.ArrayList
import java.util.Arrays
import java.util.Collections
import java.util.HashMap
import java.util.regex.Matcher
import java.util.regex.Pattern
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream
import com.fusenetworks.fuse.Fuse
import com.fusenetworks.fuse.util.NLog
import com.fusenetworks.fuse.util.NUtil
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandException
import org.bukkit.command.CommandMap
import org.bukkit.command.CommandSender
import org.bukkit.command.PluginIdentifiableCommand
import org.bukkit.plugin.Plugin
import com.fusenetworks.fuse.Fuse.Companion.plugin

// Credit to TF

class CMD_Loader private constructor() {

    init {
        throw AssertionError()
    }

    class PC_CommandInfo(val commandClass: Class<*>, val commandName: String, val source: SourceType, val blockHostConsole: Boolean, val description: String, val usage: String, aliases: String) {
        private val aliases: List<String>

        init {
            this.aliases = if ("" == aliases) ArrayList() else Arrays.asList(*aliases.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray())
        }

        fun getAliases(): List<String> {
            return Collections.unmodifiableList(aliases)
        }

        override fun toString(): String {
            val sb = StringBuilder()
            sb.append("commandName: ").append(commandName)
            sb.append("\ncommandClass: ").append(commandClass.name)
            sb.append("\nsource: ").append(source)
            sb.append("\nblock_host_console: ").append(blockHostConsole)
            sb.append("\ndescription: ").append(description)
            sb.append("\nusage: ").append(usage)
            sb.append("\naliases: ").append(aliases)
            return sb.toString()
        }
    }

    class PC_DynamicCommand constructor(val commandInfo: PC_CommandInfo) : Command(commandInfo.commandName, "Admin command/Normal player command", commandInfo.usage, commandInfo.getAliases()), PluginIdentifiableCommand {

        override fun execute(sender: CommandSender, commandLabel: String, args: Array<String>): Boolean {
            var success = false

            if (!plugin.isEnabled) {
                return false
            }

            try {
                success = plugin.onCommand(sender, this, commandLabel, args)
            } catch (ex: Throwable) {
                throw CommandException("Unhandled exception executing command '" + commandLabel + "' in plugin " + plugin.description.fullName, ex)
            }

            if (!success && usage.length > 0) {
                for (line in usage.replace("<command>", commandLabel).split("\n".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()) {
                    sender.sendMessage(line)
                }
            }

            return success
        }

        override fun getPlugin(): Plugin {
            return Fuse()
        }
    }

    companion object {
        val COMMAND_PATTERN: Pattern
        private val COMMAND_LIST: MutableList<PC_CommandInfo>

        init {
            COMMAND_PATTERN = Pattern.compile(CMD_Handler.COMMAND_PATH.replace('.', '/') + "/(" + CMD_Handler.COMMAND_PREFIX + "[^\\$]+)\\.class")
            COMMAND_LIST = ArrayList()
        }

        fun scan() {
            val commandMap = commandMap ?: return
            COMMAND_LIST.clear()
            COMMAND_LIST.addAll(commands)

            COMMAND_LIST.stream().map { commandInfo -> PC_DynamicCommand(commandInfo) }.forEach { dynamicCommand ->
                val existing = commandMap.getCommand(dynamicCommand.name)
                if (existing != null) {
                    unregisterCommand(existing, commandMap)
                }

                commandMap.register(Fuse.plugin.description.name, dynamicCommand)
            }
        }

        fun unregisterCommand(commandName: String) {
            val commandMap = commandMap
            if (commandMap != null) {
                val command = commandMap.getCommand(commandName.toLowerCase())
                if (command != null) {
                    unregisterCommand(command, commandMap)
                }
            }
        }

        fun unregisterCommand(command: Command, commandMap: CommandMap) {
            try {
                command.unregister(commandMap)
                val knownCommands = getKnownCommands(commandMap)
                if (knownCommands != null) {
                    knownCommands.remove(command.name)
                    command.aliases.stream().forEach { alias -> knownCommands.remove(alias) }
                }
            } catch (ex: Exception) {
                NLog.severe(ex)
            }

        }

        val commandMap: CommandMap?
            get() {
                val commandMap = NUtil.getField<Any>(Bukkit.getServer().pluginManager, "Fuse")
                if (commandMap != null) {
                    if (commandMap is CommandMap) {
                        return commandMap
                    }
                }
                return null
            }

        fun getKnownCommands(commandMap: CommandMap): HashMap<String, Command>? {
            val knownCommands = NUtil.getField<Any>(commandMap, "Fuse")
            if (knownCommands != null) {
                if (knownCommands is HashMap<*, *>) {
                    return knownCommands as HashMap<String, Command>
                }
            }
            return null
        }

        private val commands: List<PC_CommandInfo>
            get() {
                val commandList = ArrayList<PC_CommandInfo>()

                try {
                    val codeSource = Fuse::class.java.protectionDomain.codeSource
                    if (codeSource != null) {
                        val zip = ZipInputStream(codeSource.location.openStream())
                        var zipEntry: ZipEntry
                        while ((zipEntry = zip.nextEntry) != null) {
                            val entryName = zipEntry.name
                            val matcher = COMMAND_PATTERN.matcher(entryName)
                            if (matcher.find()) {
                                try {
                                    val commandClass = Class.forName(CMD_Handler.COMMAND_PATH + "." + matcher.group(1))

                                    val commandPermissions = commandClass.getAnnotation(CommandPermissions::class.java)
                                    val commandParameters = commandClass.getAnnotation(CommandParameters::class.java)

                                    if (commandPermissions != null && commandParameters != null) {
                                        val commandInfo = PC_CommandInfo(
                                                commandClass,
                                                matcher.group(1).split("_".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[1],
                                                commandPermissions.source(),
                                                commandPermissions.blockHostConsole(),
                                                commandParameters.description(),
                                                commandParameters.usage(),
                                                commandParameters.aliases())

                                        commandList.add(commandInfo)
                                    }
                                } catch (ex: Exception) {
                                    NLog.severe(ex)
                                }

                            }
                        }
                    }
                } catch (ex: IOException) {
                    NLog.severe(ex)
                }

                return commandList
            }
    }
}