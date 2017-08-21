package com.fusenetworks.fuse.commands

import com.fusenetworks.fuse.util.NUtil
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

abstract class Command_launchpads : CommandExecutor {
    fun run(sender: CommandSender, sender_p: Player, cmd: Command, commandLabel: String, args: Array<String>, senderIsConsole: Boolean): Boolean {
        val enabled = Bukkit.getPluginManager().getPlugin("Fuse").config.getString("launchpads.enabled")

        if (!sender.hasPermission("fuse.launchpads")) {
            sender.sendMessage(Messages.MSG_NO_PERMS)
            return true
        }

        if (args.size != 1) {
            return false
        }

        if (args[0].equals("enable", ignoreCase = true) || args[0].equals("on", ignoreCase = true)) {
            NUtil.adminAction(sender.name, "Enabling launchpads", false)
            Bukkit.getPluginManager().getPlugin("Fuse").config.set("launchpads.enabled", "true")
            Bukkit.getPluginManager().getPlugin("Fuse").saveConfig()
            Bukkit.getPluginManager().getPlugin("Fuse").reloadConfig()
            return true
        }
        if (args[0].equals("disable", ignoreCase = true) || args[0].equals("off", ignoreCase = true)) {
            NUtil.adminAction(sender.name, "Disabling launchpads", false)
            Bukkit.getPluginManager().getPlugin("Fuse").config.set("launchpads.enabled", "false")
            Bukkit.getPluginManager().getPlugin("Fuse").saveConfig()
            Bukkit.getPluginManager().getPlugin("Fuse").reloadConfig()
            return true
        }
        if (args[0].equals("status", ignoreCase = true)) {
            sender.sendMessage(ChatColor.BLUE.toString() + "Launchpads enabled: " + enabled)
            return true
        }
        return true
    }
}
