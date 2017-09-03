package com.fusenetworks.fuse.commands

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

object Developer : CommandExecutor {
    override fun onCommand(sender: CommandSender?, cmd: Command?, lbl: String?, args: Array<out String>?): Boolean {
        val dev = Bukkit.getPluginManager().getPlugin("Fuse").config.getString("server.dev")
        val superusers = Bukkit.getPluginManager().getPlugin("Fuse").config.getString("players.superusers")

        if (args!!.size != 1) {
            return false
        }

        if (superusers.contains(sender!!.name)) {
            if (args[0].equals("enable", ignoreCase = true) || args[0].equals("on", ignoreCase = true)) {
                sender.sendMessage(ChatColor.BLUE.toString() + "Enabled dev mode")
                Bukkit.getPluginManager().getPlugin("Fuse").config.set("server.dev", "true")
                Bukkit.getPluginManager().getPlugin("Fuse").saveConfig()
                Bukkit.getPluginManager().getPlugin("Fuse").reloadConfig()
                return true
            }
            if (args[0].equals("disable", ignoreCase = true) || args[0].equals("off", ignoreCase = true)) {
                sender.sendMessage(ChatColor.BLUE.toString() + "Disabled dev mode")
                Bukkit.getPluginManager().getPlugin("Fuse").config.set("server.dev", "false")
                Bukkit.getPluginManager().getPlugin("Fuse").saveConfig()
                Bukkit.getPluginManager().getPlugin("Fuse").reloadConfig()
                return true
            }
            if (args[0].equals("status", ignoreCase = true)) {
                sender.sendMessage(ChatColor.BLUE.toString() + "Developer mode: " + dev)
                return true
            }
            return true
        } else {
            sender.sendMessage(Messages.MSG_NO_PERMS)
        }
        return true
    }
}
