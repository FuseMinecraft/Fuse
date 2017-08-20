package com.fusenetworks.fuse.commands

import com.fusenetworks.fuse.util.NUtil
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.ChatColor

@CommandPermissions(source = SourceType.BOTH)
@CommandParameters(description = "Enable or disable launchpads", usage = "/<command> [on | off | status]")
class Command_launchpads : BaseCommand() {
    override fun run(sender: CommandSender, sender_p: Player, cmd: Command, commandLabel: String, args: Array<String>, senderIsConsole: Boolean): Boolean {
        val enabled = plugin.config.getString("launchpads.enabled")

        if (!sender.hasPermission("fuse.launchpads")) {
            sender.sendMessage(Messages.MSG_NO_PERMS)
            return true
        }

        if (args.size != 1) {
            return false
        }

        if (args[0].equals("enable", ignoreCase = true) || args[0].equals("on", ignoreCase = true)) {
            NUtil.adminAction(sender.name, "Enabling launchpads", false)
            plugin.config.set("launchpads.enabled", "true")
            plugin.saveConfig()
            plugin.reloadConfig()
            return true
        }
        if (args[0].equals("disable", ignoreCase = true) || args[0].equals("off", ignoreCase = true)) {
            NUtil.adminAction(sender.name, "Disabling launchpads", false)
            plugin.config.set("launchpads.enabled", "false")
            plugin.saveConfig()
            plugin.reloadConfig()
            return true
        }
        if (args[0].equals("status", ignoreCase = true)) {
            sender.sendMessage(ChatColor.BLUE.toString() + "Launchpads enabled: " + enabled)
            return true
        }
        return true
    }
}
