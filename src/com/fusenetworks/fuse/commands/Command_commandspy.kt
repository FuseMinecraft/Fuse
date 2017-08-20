package com.fusenetworks.fuse.commands

import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.ChatColor
import com.fusenetworks.fuse.util.PlayerData

// Credit to TFM devs

@CommandPermissions(source = SourceType.ONLY_IN_GAME)
@CommandParameters(description = "Spy on commands", usage = "/<command>", aliases = "cmdspy")
class Command_commandspy : BaseCommand() {
    override fun run(sender: CommandSender, sender_p: Player, cmd: Command, commandLabel: String, args: Array<String>, senderIsConsole: Boolean): Boolean {
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
