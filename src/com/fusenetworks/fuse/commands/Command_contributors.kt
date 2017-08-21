package com.fusenetworks.fuse.commands

import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

abstract class Command_contributors : CommandExecutor {
    fun run(sender: CommandSender, sender_p: Player, cmd: Command, commandLabel: String, args: Array<String>, senderIsConsole: Boolean): Boolean {
        sender.sendMessage(ChatColor.RED.toString() + "Contributors to Fuse:\n" +
                "Telesphoreo - Developer\n" +
                "OxLemonxO - Developer\n" +
                "Madgeek1450, Prozza - TotalFreedomMod code\n" +
                "TheMinecraft (_Windows) - Unload chunks command")
        return true
    }
}
