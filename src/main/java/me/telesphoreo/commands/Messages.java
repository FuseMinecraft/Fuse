package me.telesphoreo.commands;

import org.bukkit.ChatColor;
import org.spigotmc.SpigotConfig;

public class Messages
{
    static final String MSG_NO_PERMS = ChatColor.RED + "I'm sorry but you do not have permission to perform this command. Please contact the server administrator if you believe that this is in error.";    public static final String PLAYER_NOT_FOUND = ChatColor.RED + "Player not found";
    static final String PLAYER_ONLY = ChatColor.RED + "This command can only be executed by a player";
    static final String CONSOLE_ONLY = ChatColor.RED + "This command can only be executed by the console";
    static final String NO_MSG = ChatColor.RED + "Please provide a message";
    static final String RESTRICTED_TO_SUPERUSERS = ChatColor.RED + "This command is restricted to superusers";
    static final String UNKNOWN_COMMAND = SpigotConfig.unknownCommandMessage;
}
