package com.fusenetworks.fuse.commands

import org.apache.commons.lang3.StringUtils
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin

// Credit to TF

abstract class Command_pluginmanage : CommandExecutor {
    fun run(sender: CommandSender, sender_p: Player, cmd: Command, commandLabel: String, args: Array<String>, senderIsConsole: Boolean): Boolean {
        run {
            if (!sender.hasPermission("fuse.pluginmanage")) {
                sender.sendMessage(Messages.MSG_NO_PERMS)
                return true
            }
            if (args.isEmpty() || args.size > 2) {
                return false
            }

            val pm = Bukkit.getServer().pluginManager

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

                if (target.name == Bukkit.getPluginManager().getPlugin("Fuse").name) {
                    sender.sendMessage(ChatColor.GRAY.toString() + "You cannot disable " + Bukkit.getPluginManager().getPlugin("Fuse").name)
                    return true
                }

                if (target.name == "ViaVersion" || target.name == "ViaBackwards") {
                    sender.sendMessage(ChatColor.RED.toString() + "You cannot disable " + args[1])
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
        Bukkit.getServer().pluginManager.plugins
                .filter { it.name.equals(name, ignoreCase = true) }
                .forEach { return it }

        if (name.length >= 3) {
            Bukkit.getServer().pluginManager.plugins
                    .filter { it.name.toLowerCase().contains(name.toLowerCase()) }
                    .forEach { return it }
        }
        return null
    }
}