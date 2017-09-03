package com.fusenetworks.fuse.commands

import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

object Contributors : CommandExecutor {
    override fun onCommand(sender: CommandSender?, cmd: Command?, lbl: String?, args: Array<out String>?): Boolean {
        sender?.sendMessage(ChatColor.RED.toString() + "Contributors to Fuse:\n" +
                "Telesphoreo - Developer\n" +
                "OxLemonxO - Developer\n" +
                "Madgeek1450, Prozza - TotalFreedomMod code\n" +
                "TheMinecraft (_Windows) - Unload chunks command")
        return true
    }
}
