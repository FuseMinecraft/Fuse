package com.fusenetworks.fuse.commands

import org.apache.commons.lang.StringUtils
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

abstract class Command_adminchat : CommandExecutor {

    fun run(sender: CommandSender, sender_p: Player, cmd: Command, commandLabel: String, args: Array<String>, senderIsConsole: Boolean): Boolean {
        if (sender.hasPermission("fuse.adminchat")) {
            if (sender is Player)
            // needs cleanup
            {
                if (args.size == 0) {
                    sender.sendMessage(Messages.NO_MSG)
                    return true
                }
                if (sender.hasPermission("fuse.moderator")) {
                    NUtil.moderatorAdminChat(sender.getName(), StringUtils.join(args, " "))
                    return true
                }
                //
                if (sender.hasPermission("fuse.admin")) {
                    NUtil.adminAdminChat(sender.getName(), StringUtils.join(args, " "))
                    return true
                }
                //
                if (sender.hasPermission("fuse.developer")) {
                    NUtil.devAdminChat(sender.getName(), StringUtils.join(args, " "))
                    return true
                }
                //
                if (sender.hasPermission("fuse.builder")) {
                    NUtil.builderAdminChat(sender.getName(), StringUtils.join(args, " "))
                    return true
                }
                //
                if (sender.hasPermission("fuse.owner")) {
                    NUtil.ownerAdminChat(sender.getName(), StringUtils.join(args, " "))
                    return true
                }
                //
                NLog.info("[AdminChat] " + sender.getName() + ": " + StringUtils.join(args, " "))
                return true
            } else {
                NUtil.consoleAdminChat(sender.name, StringUtils.join(args, " "))
                NLog.info("[AdminChat] " + sender.name + " [Console]: " + StringUtils.join(args, " "))
            }
        } else {
            if (sender.isOp) {
                NUtil.nullAdminChat(sender.name, StringUtils.join(args, " "))
            } else {
                sender.sendMessage(Messages.MSG_NO_PERMS)
            }
        }
        return true
    }
}
