package com.fusenetworks.fuse.commands

import com.fusenetworks.fuse.util.NLog
import com.fusenetworks.fuse.Fuse
import org.apache.commons.lang3.StringUtils
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

// Credit to TF

object CMD_Handler {
    val COMMAND_PATH = BaseCommand::class.java.`package`.name // "com.packsnetwork.packscore.commands"
    val COMMAND_PREFIX = "Command_"

    fun handleCommand(sender: CommandSender, cmd: Command, commandLabel: String, args: Array<String>): Boolean {
        val playerSender: Player?
        val senderIsConsole: Boolean

        if (sender is Player) {
            senderIsConsole = false
            playerSender = sender

            NLog.info(String.format("[PLAYER_COMMAND] %s (%s): /%s %s",
                    playerSender.name,
                    ChatColor.stripColor(playerSender.displayName),
                    commandLabel,
                    StringUtils.join(args, " ")), true)
        } else {
            senderIsConsole = true
            playerSender = null

            NLog.info(String.format("[CONSOLE_COMMAND] %s: /%s %s",
                    sender.name,
                    commandLabel,
                    StringUtils.join(args, " ")), true)
        }

        val dispatcher: BaseCommand
        try {
            val classLoader = Fuse::class.java.classLoader
            dispatcher = classLoader.loadClass(String.format("%s.%s%s",
                    COMMAND_PATH,
                    COMMAND_PREFIX,
                    cmd.name.toLowerCase())).newInstance() as BaseCommand
            dispatcher.setup(Fuse.plugin, sender, dispatcher.javaClass)
        } catch (ex: ClassNotFoundException) {
            NLog.severe("Could not load command: " + cmd.name)
            NLog.severe(ex)

            sender.sendMessage(ChatColor.RED.toString() + "Command Error! Could not load command: " + cmd.name)
            return true
        } catch (ex: InstantiationException) {
            NLog.severe("Could not load command: " + cmd.name)
            NLog.severe(ex)
            sender.sendMessage(ChatColor.RED.toString() + "Command Error! Could not load command: " + cmd.name)
            return true
        } catch (ex: IllegalAccessException) {
            NLog.severe("Could not load command: " + cmd.name)
            NLog.severe(ex)
            sender.sendMessage(ChatColor.RED.toString() + "Command Error! Could not load command: " + cmd.name)
            return true
        }

        try {
            return dispatcher.run(sender, playerSender!!, cmd, commandLabel, args, senderIsConsole)
        } catch (ex: Exception) {
            NLog.severe("Command Error: " + commandLabel)
            NLog.severe(ex)
            sender.sendMessage(ChatColor.RED.toString() + "Command Error: " + ex.message)
        }

        return true
    }
}