package com.fusenetworks.fuse.commands

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

object Website : CommandExecutor {
    override fun onCommand(sender: CommandSender, cmd: Command, label: String, args: Array<String>): Boolean {
        val website = Bukkit.getPluginManager().getPlugin("Fuse").config.getString("server.website")
        if (!website.equals("none", ignoreCase = true)) {
            sender.sendMessage(ChatColor.AQUA.toString() + "Website: " + website)
        } else {
            sender.sendMessage(ChatColor.RED.toString() + "There is not a website for this server")
        }
        return true
    }
}
