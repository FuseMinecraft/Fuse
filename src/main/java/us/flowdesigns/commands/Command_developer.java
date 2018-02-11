package us.flowdesigns.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(source = SourceType.BOTH)
@CommandParameters(description = "Developer command", usage = "/<command> [on | off | status]", aliases = "dev")
public class Command_developer extends BaseCommand {
    @Override
    public boolean run(final CommandSender sender, final Player sender_p, final Command cmd, final String commandLabel, final String[] args, final boolean senderIsConsole)
    {
        String dev = plugin.getConfig().getString("server.dev");
        String superusers = plugin.getConfig().getString("players.superusers");

        if (!superusers.contains(sender.getName()))
        {
            sender.sendMessage(ChatColor.RED + "This command is restricted to superusers.");
            return true;
        }

        if (superusers.contains(sender.getName()))
        {
            if (args.length != 1)
            {
                return false;
            }
            if (args[0].equalsIgnoreCase("enable") || args[0].equalsIgnoreCase("on"))
            {
                sender.sendMessage(ChatColor.BLUE + "Enabled dev mode");
                plugin.getConfig().set("server.dev", "true");
                plugin.saveConfig();
                plugin.reloadConfig();
                return true;
            }
            if (args[0].equalsIgnoreCase("disable") || args[0].equalsIgnoreCase("off"))
            {
                sender.sendMessage(ChatColor.BLUE + "Disabled dev mode");
                plugin.getConfig().set("server.dev", "false");
                plugin.saveConfig();
                plugin.reloadConfig();
                return true;
            }
            if (args[0].equalsIgnoreCase("status"))
            {
                sender.sendMessage(ChatColor.BLUE + "Developer mode: " + dev);
                return true;
            }
            return true;
        } else {
            sender.sendMessage(Messages.MSG_NO_PERMS);
        }
        return true;
    }
}
