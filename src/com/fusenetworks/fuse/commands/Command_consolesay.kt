package com.fusenetworks.fuse.commands

import org.apache.commons.lang3.StringUtils
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

@CommandPermissions(source = SourceType.ONLY_CONSOLE)
@CommandParameters(description = "Chat from the console", usage = "/<command>", aliases = "csay")
abstract class Command_consolesay : BaseCommand() {
    override fun run(sender: CommandSender, sender_p: Player, cmd: Command, commandLabel: String, args: Array<String>, senderIsConsole: Boolean): Boolean {
        if (args.size == 0) {
            if (senderIsConsole) {
                sender.sendMessage(Messages.NO_MSG)
                return true
            } else {
                sender.sendMessage(Messages.CONSOLE_ONLY)
            }
            return true
        }
        if (args.size > 0 && senderIsConsole) {
            Bukkit.broadcastMessage(String.format("§7[CONSOLE]§f<§c%s§f> %s", sender.name, StringUtils.join(args, " ")))
            return true
        } else {
            sender.sendMessage(Messages.CONSOLE_ONLY)
        }
        return true
    }
}
