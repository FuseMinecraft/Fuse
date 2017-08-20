package com.fusenetworks.fuse.commands

import com.fusenetworks.fuse.util.History
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

@CommandPermissions(source = SourceType.BOTH)
@CommandParameters(description = "Shows the name history of a player", usage = "/<command> <player>")
class Command_namehistory : BaseCommand() {
    override fun run(sender: CommandSender, sender_p: Player, cmd: Command, commandLabel: String, args: Array<String>, senderIsConsole: Boolean): Boolean {
        if (!sender.hasPermission("fuse.namehistory")) {
            sender.sendMessage(Messages.MSG_NO_PERMS)
            return true
        }
        if (args.size != 1) {
            return false
        }
        History.reportHistory(sender, args[0])
        return true
    }
}
