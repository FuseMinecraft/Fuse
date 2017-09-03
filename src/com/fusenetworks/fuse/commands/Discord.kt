package com.fusenetworks.fuse.commands

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

object Discord : CommandExecutor {
    override fun onCommand(sender: CommandSender?, cmd: Command?, lbl: String?, args: Array<out String>?): Boolean {
        val discord = Bukkit.getPluginManager().getPlugin("Fuse").config.getString("server.discord")
        if (!discord.equals("none", ignoreCase = true)) {
            sender?.sendMessage(ChatColor.AQUA.toString() + "Discord: " + discord)
        } else {
            sender?.sendMessage(ChatColor.RED.toString() + "There is no Discord setup for this server")
        }
        return true
    }
}
