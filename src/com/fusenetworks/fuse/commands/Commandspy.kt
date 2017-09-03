package com.fusenetworks.fuse.commands

import com.fusenetworks.fuse.util.PlayerData
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.event.Listener

// Credit to TFM devs

object Commandspy : CommandExecutor, Listener {
    override fun onCommand(sender: CommandSender?, cmd: Command?, lbl: String?, args: Array<out String>?): Boolean {
        if (sender !is Player) {
            sender?.sendMessage(Messages.PLAYER_ONLY)
            return true
        }
        if (!sender.hasPermission("fuse.commandspy")) {
            sender.sendMessage(Messages.MSG_NO_PERMS)
            return true
        } else {
            val playerdata = PlayerData.getPlayerData(sender)
            playerdata.setCommandSpy(!playerdata.cmdspyEnabled())
            sender.sendMessage(ChatColor.GRAY.toString() + "CommandSpy " + if (playerdata.cmdspyEnabled()) "enabled" else "disabled")
            return true
        }
    }
}