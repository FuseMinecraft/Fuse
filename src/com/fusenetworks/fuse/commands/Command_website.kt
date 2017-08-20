package com.fusenetworks.fuse.commands

import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.ChatColor

@CommandPermissions(source = SourceType.BOTH)
@CommandParameters(description = "Gives a link to the website", usage = "/<command>")
class Command_website : BaseCommand() {
    override fun run(sender: CommandSender, sender_p: Player, cmd: Command, commandLabel: String, args: Array<String>, senderIsConsole: Boolean): Boolean {
        val website = plugin.config.getString("server.website")
        if (!website.equals("none", ignoreCase = true)) {
            sender.sendMessage(ChatColor.AQUA.toString() + "Website: " + website)
        } else {
            sender.sendMessage(ChatColor.RED.toString() + "There is not a website for this server")
        }
        return true
    }
}
