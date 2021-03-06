package me.telesphoreo.commands;

import me.telesphoreo.Nitrogen;
import me.telesphoreo.utils.NLog;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

// Credit to TF

public class CMD_Handler
{
    public static final String COMMAND_PATH = BaseCommand.class.getPackage().getName(); // "com.packsnetwork.packscore.commands"
    public static final String COMMAND_PREFIX = "Command_";

    public static boolean handleCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
    {
        final Player playerSender;
        final boolean senderIsConsole;

        if (sender instanceof Player)
        {
            senderIsConsole = false;
            playerSender = (Player)sender;
        }
        else
        {
            senderIsConsole = true;
            playerSender = null;

            NLog.info(String.format("[CONSOLE_COMMAND] %s: /%s %s",
                    sender.getName(),
                    commandLabel,
                    StringUtils.join(args, " ")), true);
        }

        final BaseCommand dispatcher;
        try
        {
            final ClassLoader classLoader = Nitrogen.class.getClassLoader();
            dispatcher = (BaseCommand)classLoader.loadClass(String.format("%s.%s%s",
                    COMMAND_PATH,
                    COMMAND_PREFIX,
                    cmd.getName().toLowerCase())).newInstance();
            dispatcher.setup(Nitrogen.plugin, sender, dispatcher.getClass());
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