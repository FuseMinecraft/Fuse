package com.fusenetworks.fuse.commands

import org.apache.commons.lang3.StringUtils
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

object Consolesay : CommandExecutor {
    override fun onCommand(sender: CommandSender?, cmd: Command?, lbl: String?, args: Array<out String>?): Boolean {
        if (args!!.isEmpty()) {
            if (sender !is Player) {
                sender?.sendMessage(Messages.NO_MSG)
                return true
            } else {
                sender.sendMessage(Messages.CONSOLE_ONLY)
            }
            return true
        }
        if (args.isNotEmpty() && sender !is Player) {
            Bukkit.broadcastMessage(String.format("§7[CONSOLE]§f<§c%s§f> %s", sender?.name, StringUtils.join(args, " ")))
            return true
        } else {
            sender?.sendMessage(Messages.CONSOLE_ONLY)
        }
        return true
    }
}
