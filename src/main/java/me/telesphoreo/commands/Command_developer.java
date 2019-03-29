package me.telesphoreo.commands;

import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(source = SourceType.BOTH)
@CommandParameters(description = "Developer command", usage = "/<command> [on | off | status]", aliases = "dev")
public class Command_developer extends BaseCommand
{
    @Override
    public boolean run(final CommandSender sender, final Player sender_p, final Command cmd, final String commandLabel, final String[] args, final boolean senderIsConsole)
    {
        boolean dev = plugin.getConfig().getBoolean("server.dev");
        List superusers = plugin.getConfig().getList("players.superusers");

        if (superusers.contains(sender.getName()))
        {
            if (args.length != 1)
            {
                return false;
            }
            if (args[0].equalsIgnoreCase("enable") || args[0].equalsIgnoreCase("on"))
            {
                sender.sendMessage(ChatColor.BLUE + "Enabled developer mode");
                plugin.getConfig().set("server.dev", true);
                plugin.saveConfig();
                plugin.reloadConfig();
                return true;
            }
            if (args[0].equalsIgnoreCase("disable") || args[0].equalsIgnoreCase("off"))
            {
                sender.sendMessage(ChatColor.BLUE + "Disabled developer mode");
                plugin.getConfig().set("server.dev", false);
                plugin.saveConfig();
                plugin.reloadConfig();
                return true;
            }
            if (args[0].equalsIgnoreCase("status"))
            {
                sender.sendMessage(ChatColor.BLUE + (dev ? "Developer mode is enabled" : "Developer mode is disabled"));
                return true;
            }
            return false;
        }
        else
        {
            sender.sendMessage(Messages.RESTRICTED_TO_SUPERUSERS);
        }
        return true;
    }
}
