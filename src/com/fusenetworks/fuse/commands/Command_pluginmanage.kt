package com.fusenetworks.fuse.commands

import org.apache.commons.lang3.StringUtils
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.PluginManager

// Credit to TF

@CommandPermissions(source = SourceType.BOTH)
@CommandParameters(description = "Manage plugins", usage = "/<command> <<enable | disable | reload> <pluginname>> | list>", aliases = "plc,pm")
class Command_pluginmanage : BaseCommand() {
    override fun run(sender: CommandSender, sender_p: Player, cmd: Command, commandLabel: String, args: Array<String>, senderIsConsole: Boolean): Boolean {
        run {
            if (!sender.hasPermission("fuse.pluginmanage")) {
                sender.sendMessage(Messages.MSG_NO_PERMS)
                return true
            }
            if (args.size == 0 || args.size > 2) {
                return false
            }

            val pm = server.pluginManager

            if (args.size == 1) {
                if (args[0].equals("list", ignoreCase = true)) {
                    for (serverPlugin in pm.plugins) {
                        val version = serverPlugin.description.version
                        sender.sendMessage(ChatColor.GRAY.toString() + "- " + (if (serverPlugin.isEnabled) ChatColor.GREEN else ChatColor.RED) + serverPlugin.name
                                + ChatColor.GOLD + (if (version != null && !version.isEmpty()) " v" + version else "") + " by "
                                + StringUtils.join(serverPlugin.description.authors, ", "))
                    }

                    return true
                }

                return false
            }

            if ("enable" == args[0]) {
                val target = getPlugin(args[1])
                if (target == null) {
                    sender.sendMessage(ChatColor.GRAY.toString() + "Plugin not found!")
                    return true
                }

                if (target.isEnabled) {
                    sender.sendMessage(ChatColor.GRAY.toString() + "Plugin is already enabled.")
                    return true
                }

                pm.enablePlugin(target)

                if (!pm.isPluginEnabled(target)) {
                    sender.sendMessage(ChatColor.GRAY.toString() + "Error enabling plugin " + target.name)
                    return true
                }

                sender.sendMessage(ChatColor.GRAY.toString() + target.name + " is now enabled.")
                return true
            }

            if ("disable" == args[0]) {
                val target = getPlugin(args[1])
                if (target == null) {
                    sender.sendMessage(ChatColor.GRAY.toString() + "Plugin not found!")
                    return true
                }

                if (!target.isEnabled) {
                    sender.sendMessage(ChatColor.GRAY.toString() + "Plugin is already disabled.")
                    return true
                }

                if (target.name == plugin.name) {
                    sender.sendMessage(ChatColor.GRAY.toString() + "You cannot disable " + plugin.name)
                    return true
                }

                if (target.name == "ViaVersion") {
                    sender.sendMessage(ChatColor.RED.toString() + "You cannot disable ViaVersion")
                    return true
                }

                pm.disablePlugin(target)

                if (pm.isPluginEnabled(target)) {
                    sender.sendMessage(ChatColor.GRAY.toString() + "Error disabling plugin " + target.name)
                    return true
                }

                sender.sendMessage(ChatColor.GRAY.toString() + target.name + " is now disabled.")
                return true
            }

            if ("reload" == args[0]) {
                val target = getPlugin(args[1])
                if (target == null) {
                    sender.sendMessage(ChatColor.GRAY.toString() + "Plugin not found!")
                    return true
                }

                if (target.name == "ViaVersion") {
                    sender.sendMessage(ChatColor.RED.toString() + "You cannot reload ViaVersion")
                    return true
                }

                pm.disablePlugin(target)
                pm.enablePlugin(target)
                sender.sendMessage(ChatColor.GRAY.toString() + target.name + " reloaded.")
                return true
            }

            return false
        }
    }

    fun getPlugin(name: String): Plugin? {
        for (serverPlugin in server.pluginManager.plugins) {
            if (serverPlugin.name.equals(name, ignoreCase = true)) {
                return serverPlugin
            }
        }

        if (name.length >= 3) {
            for (serverPlugin in server.pluginManager.plugins) {
                if (serverPlugin.name.toLowerCase().contains(name.toLowerCase())) {
                    return serverPlugin
                }
            }
        }

        return null
    }
}