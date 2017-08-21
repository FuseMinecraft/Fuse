package com.fusenetworks.fuse.commands

import com.fusenetworks.fuse.util.PlayerData
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

// Credit to TFM devs

abstract class Command_commandspy : CommandExecutor {
    fun run(sender: CommandSender, sender_p: Player, cmd: Command, commandLabel: String, args: Array<String>, senderIsConsole: Boolean): Boolean {
        if (senderIsConsole) {
            sender.sendMessage(Messages.PLAYER_ONLY)
            return true
        }
        if (!sender.hasPermission("fuse.cmdspy")) {
            sender.sendMessage(Messages.MSG_NO_PERMS)
            return true
        } else {
            val playerdata = PlayerData.getPlayerData(sender_p)
            playerdata.setCommandSpy(!playerdata.cmdspyEnabled())
            sender.sendMessage(ChatColor.GRAY.toString() + "CommandSpy " + if (playerdata.cmdspyEnabled()) "enabled" else "disabled")
            return true
        }
    }
}
