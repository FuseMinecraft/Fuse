package com.fusenetworks.fuse.commands

import org.bukkit.ChatColor

object Messages {
    val MSG_NO_PERMS = ChatColor.GOLD.toString() + "Fuse >> You do not have permission to do this"
    val INVALID_SERVER = ChatColor.GOLD.toString() + "Fuse >> You are not allowed to use this command on this server"
    val PLAYER_NOT_FOUND = ChatColor.GOLD.toString() + "Fuse >> Player not found"
    val PLAYER_ONLY = ChatColor.GOLD.toString() + "Fuse >> This command can only be executed by a player"
    val CONSOLE_ONLY = ChatColor.GOLD.toString() + "Fuse >> This command can only be executed by the console"
    val NO_MSG = ChatColor.GOLD.toString() + "Fuse >> Please provide a message"
}
