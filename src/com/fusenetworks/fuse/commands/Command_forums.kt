package com.fusenetworks.fuse.commands

import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.ChatColor

@CommandPermissions(source = SourceType.BOTH)
@CommandParameters(description = "Gives a link to the forums", usage = "/<command>", aliases = "forum,forums,fourm,fourms")
class Command_forums : BaseCommand() {
    override fun run(sender: CommandSender, sender_p: Player, cmd: Command, commandLabel: String, args: Array<String>, senderIsConsole: Boolean): Boolean {
        val forums = plugin.config.getString("server.forums")
        if (!forums.equals("none", ignoreCase = true)) {
            sender.sendMessage(ChatColor.AQUA.toString() + "Forums: " + forums)
        } else {
            sender.sendMessage(ChatColor.RED.toString() + "There are no forums setup for this server")
        }
        return true
    }
}
