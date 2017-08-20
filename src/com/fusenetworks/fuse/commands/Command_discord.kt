package com.fusenetworks.fuse.commands

import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.ChatColor

@CommandPermissions(source = SourceType.BOTH)
@CommandParameters(description = "Gives a link to the Discord server", usage = "/<command>")
class Command_discord : BaseCommand() {
    override fun run(sender: CommandSender, sender_p: Player, cmd: Command, commandLabel: String, args: Array<String>, senderIsConsole: Boolean): Boolean {
        val discord = plugin.config.getString("server.discord")
        if (!discord.equals("none", ignoreCase = true)) {
            sender.sendMessage(ChatColor.AQUA.toString() + "Discord: " + discord)
        } else {
            sender.sendMessage(ChatColor.RED.toString() + "There is no Discord setup for this server")
        }
        return true
    }
}
