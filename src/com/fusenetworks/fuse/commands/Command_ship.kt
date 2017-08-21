package com.fusenetworks.fuse.commands

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

abstract class Command_ship : CommandExecutor {
    fun run(sender: CommandSender, sender_p: Player, cmd: Command, commandLabel: String, args: Array<String>, senderIsConsole: Boolean): Boolean {
        if (!sender.hasPermission("fuse.ship")) {
            sender.sendMessage(Messages.MSG_NO_PERMS)
            return true
        }
        if (args.size != 2) {
            sender.sendMessage(ChatColor.RED.toString() + "Invalid Usage: /ship <player1> <player2>")
            return true
        }

        val notPacks = args[0]
        val definitelyNotLemonHesForeverAlone = args[1]

        if (notPacks == definitelyNotLemonHesForeverAlone) {
            sender.sendMessage(ChatColor.RED.toString() + "You can't ship the same person with the same person.")
            return true
        }

        Bukkit.broadcastMessage(ChatColor.GREEN.toString() + "" + sender_p.name + " ships " + notPacks + " x " + definitelyNotLemonHesForeverAlone + "." + ChatColor.LIGHT_PURPLE + " <3")
        return true
    }
}
