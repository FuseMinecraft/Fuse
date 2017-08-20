package com.fusenetworks.fuse.commands

import com.fusenetworks.fuse.util.NUtil
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

@CommandPermissions(source = SourceType.BOTH)
@CommandParameters(description = "Clears lag", usage = "/<command> [-s]", aliases = "entitywipe,ew,rd")
class Command_clearlag : BaseCommand() {
    override fun run(sender: CommandSender, sender_p: Player, cmd: Command, commandLabel: String, args: Array<String>, senderIsConsole: Boolean): Boolean {
        if (!sender.hasPermission("fuse.clearlag")) {
            sender.sendMessage(Messages.MSG_NO_PERMS)
            return true
        }

        if (args.size == 0) {
            NUtil.adminAction(sender.name, "Removing all server entities", false)
            if (NUtil.NEntityWiper.wipeEntities(true, true) == 1) {
                NUtil.playerMsg(sender, NUtil.NEntityWiper.wipeEntities(true, true).toString() + " entity removed", ChatColor.GRAY)
                return true
            } else {
                NUtil.playerMsg(sender, NUtil.NEntityWiper.wipeEntities(true, true).toString() + " entities removed", ChatColor.GRAY)
            }
            return true
        } else if (args[0].equals("-s", ignoreCase = true)) {
            if (NUtil.NEntityWiper.wipeEntities(true, true) == 1) {
                NUtil.playerMsg(sender, NUtil.NEntityWiper.wipeEntities(true, true).toString() + " entity removed", ChatColor.GRAY)
                return true
            } else {
                NUtil.playerMsg(sender, NUtil.NEntityWiper.wipeEntities(true, true).toString() + " entities removed", ChatColor.GRAY)
            }
        } else {
            return false
        }
        return true
    }
}