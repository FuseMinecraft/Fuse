package com.fusenetworks.fuse.commands;

import com.fusenetworks.fuse.util.NLog;
import com.fusenetworks.fuse.Fuse;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

// Credit to TF

public class CMD_Handler
{
    public static final String COMMAND_PATH = BaseCommand.class.getPackage().getName();
    public static final String COMMAND_PREFIX = "Command_";

    public static boolean handleCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
    {
        final Player playerSender;
        final boolean senderIsConsole;
        senderIsConsole = false;
        playerSender = (Player) sender;

        final BaseCommand dispatcher;
        try
        {
            final ClassLoader classLoader = Fuse.class.getClassLoader();
            dispatcher = (BaseCommand) classLoader.loadClass(String.format("%s.%s%s",
                    COMMAND_PATH,
                    COMMAND_PREFIX,
                    cmd.getName().toLowerCase())).newInstance();
            dispatcher.setup(Fuse.plugin, sender, dispatcher.getClass());
        }
        catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex)
        {
            NLog.severe("Could not load command: " + cmd.getName());
            NLog.severe(ex);

            sender.sendMessage(ChatColor.RED + "Command Error! Could not load command: " + cmd.getName());
            return true;
        }

        try
        {
            return dispatcher.run(sender, playerSender, cmd, commandLabel, args, senderIsConsole);
        }
        catch (Exception ex)
        {
            NLog.severe("Command Error: " + commandLabel);
            NLog.severe(ex);
            sender.sendMessage(ChatColor.RED + "Command Error: " + ex.getMessage());
        }

        return true;
    }
}