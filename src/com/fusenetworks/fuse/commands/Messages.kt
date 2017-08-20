package com.fusenetworks.fuse.commands

import org.bukkit.ChatColor

object Messages {
    val MSG_NO_PERMS = ChatColor.GOLD.toString() + "Fuse >> You do not have permission to do this"
    val CANNOT_DO_PLAYER_ACTION = ChatColor.GOLD.toString() + "Fuse >> You cannot do that action on that player"
    val INVALID_SERVER = ChatColor.GOLD.toString() + "Fuse >> You are not allowed to use this command on this server"
    val PLAYER_NOT_FOUND = ChatColor.GOLD.toString() + "Fuse >> Player not found"
    val PLAYER_ONLY = ChatColor.GOLD.toString() + "Fuse >> This command can only be executed by a player"
    val CONSOLE_ONLY = ChatColor.GOLD.toString() + "Fuse >> This command can only be executed by the console"
    val NO_MSG = ChatColor.GOLD.toString() + "Fuse >> Please provide a message"
    val INVALID_KIT = ChatColor.GOLD.toString() + "Fuse >> That is not a valid kit. Make sure you did not misspell it"
}
