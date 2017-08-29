package com.fusenetworks.fuse.commands

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

object PlayerCmd : CommandExecutor {
    override fun onCommand(sender: CommandSender?, cmd: Command?, lbl: String?, args: Array<out String>?): Boolean {
        (sender as? Player)?.chat("I just ran the /player command") ?: sender?.sendMessage("You aren't a player :(")
        return true
    }
}