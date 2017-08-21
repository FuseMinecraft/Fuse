package com.fusenetworks.fuse.commands

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

abstract class Command_forums : CommandExecutor {
    fun run(sender: CommandSender, sender_p: Player, cmd: Command, commandLabel: String, args: Array<String>, senderIsConsole: Boolean): Boolean {
        val forums = Bukkit.getPluginManager().getPlugin("Fuse").config.getString("server.forums")
        if (!forums.equals("none", ignoreCase = true)) {
            sender.sendMessage(ChatColor.AQUA.toString() + "Forums: " + forums)
        } else {
            sender.sendMessage(ChatColor.RED.toString() + "There are no forums setup for this server")
        }
        return true
    }
}
