package com.fusenetworks.fuse.commands

import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.ChatColor

@CommandPermissions(source = SourceType.ONLY_IN_GAME)
@CommandParameters(description = "Tells a person how to apply for admin", usage = "/<command>", aliases = "ai, apply")
abstract class Command_admininfo : BaseCommand() {
    override fun run(sender: CommandSender, sender_p: Player, cmd: Command, commandLabel: String, args: Array<String>, senderIsConsole: Boolean): Boolean {
        val applications_enabled = plugin.config.getString("server.applications_enabled")
        val forums = plugin.config.getString("server.forums")
        val admin_app_template = plugin.config.getString("server.admin_application_template")
        val new_thread_link = plugin.config.getString("server.admin_app_new_thread_link")
        if (senderIsConsole) {
            sender.sendMessage(Messages.PLAYER_ONLY)
            return true
        }
        if (applications_enabled.equals("true", ignoreCase = true)
                && !forums.equals("none", ignoreCase = true)
                && !admin_app_template.equals("none", ignoreCase = true)
                && !new_thread_link.equals("none", ignoreCase = true)) {
            sender.sendMessage(ChatColor.RED.toString() + "To apply for moderator, register an account at: " + forums + "\n" +
                    "When you've registered, copy and paste: " + admin_app_template + "\n" +
                    "Create a new thread by clicking here: " + new_thread_link + "\n" +
                    "Copy and paste the template into the thread\n" +
                    "Do not bug staff members to look at your application or else it will most likely get denied")
            return true
        } else {
            sender.sendMessage(ChatColor.RED.toString() + "Currently you can not apply for moderator, sorry.")
        }
        return true
    }
}
