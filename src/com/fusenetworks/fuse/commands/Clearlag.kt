package com.fusenetworks.fuse.commands

import com.fusenetworks.fuse.util.NUtil
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

object Clearlag : CommandExecutor {
    override fun onCommand(sender: CommandSender?, cmd: Command?, lbl: String?, args: Array<out String>?): Boolean {
        if (!sender?.hasPermission("fuse.clearlag")!!) {
            sender?.sendMessage(Messages.MSG_NO_PERMS)
            return true
        }

        if (args!!.isEmpty()) {
            NUtil.adminAction(sender?.name, "Removing all server entities", false)
            if (NUtil.NEntityWiper.wipeEntities(true, true) == 1) {
                NUtil.playerMsg(sender, NUtil.NEntityWiper.wipeEntities(true, true).toString() + " entity removed", ChatColor.GRAY)
                return true
            } else {
                NUtil.playerMsg(sender, NUtil.NEntityWiper.wipeEntities(true, true).toString() + " entities removed", ChatColor.GRAY)
            }
            return true
        } else if (args!![0].equals("-s", ignoreCase = true)) {
            if (NUtil.NEntityWiper.wipeEntities(true, true) == 1) {
                NUtil.playerMsg(sender, NUtil.NEntityWiper.wipeEntities(true, true).toString() + " entity removed", ChatColor.GRAY)
                return true
            } else {
                NUtil.playerMsg(sender, NUtil.NEntityWiper.wipeEntities(true, true).toString() + " entities removed", ChatColor.GRAY)
            }
        } else {
            return false
        }
        return true
    }
}