package com.fusenetworks.fuse.commands

import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.ChatColor

@CommandPermissions(source = SourceType.BOTH)
@CommandParameters(description = "Developer command", usage = "/<command> [on | off | status]", aliases = "dev")
class Command_developer : BaseCommand() {
    override fun run(sender: CommandSender, sender_p: Player, cmd: Command, commandLabel: String, args: Array<String>, senderIsConsole: Boolean): Boolean {
        val dev = plugin.config.getString("server.dev")
        val superusers = plugin.config.getString("players.superusers")

        if (args.size != 1) {
            return false
        }

        if (superusers.contains(sender.name)) {
            if (args[0].equals("enable", ignoreCase = true) || args[0].equals("on", ignoreCase = true)) {
                sender.sendMessage(ChatColor.BLUE.toString() + "Enabled dev mode")
                plugin.config.set("server.dev", "true")
                plugin.saveConfig()
                plugin.reloadConfig()
                return true
            }
            if (args[0].equals("disable", ignoreCase = true) || args[0].equals("off", ignoreCase = true)) {
                sender.sendMessage(ChatColor.BLUE.toString() + "Disabled dev mode")
                plugin.config.set("server.dev", "false")
                plugin.saveConfig()
                plugin.reloadConfig()
                return true
            }
            if (args[0].equals("status", ignoreCase = true)) {
                sender.sendMessage(ChatColor.BLUE.toString() + "Developer mode: " + dev)
                return true
            }
            return true
        } else {
            sender.sendMessage(Messages.MSG_NO_PERMS)
        }
        return true
    }
}
