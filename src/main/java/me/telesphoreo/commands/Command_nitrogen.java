package me.telesphoreo.commands;

import me.telesphoreo.Nitrogen;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(source = SourceType.BOTH)
@CommandParameters(description = "Shows information about Nitrogen", usage = "/<command> [reload | debug | help]")
public class Command_nitrogen extends BaseCommand
{
    @Override
    public boolean run(final CommandSender sender, final Player sender_p, final Command cmd, final String commandLabel, final String[] args, final boolean senderIsConsole)
    {
        boolean spawn_on_join = plugin.getConfig().getBoolean("server.spawn_on_join");
        boolean applications_enabled = plugin.getConfig().getBoolean("commands.applications_enabled");
        boolean drop_items_on_death = plugin.getConfig().getBoolean("server.drop_items_on_death");
        boolean clear_inventory_on_join = plugin.getConfig().getBoolean("server.clear_inventory_on_join");
        boolean server_hunger = plugin.getConfig().getBoolean("server.hunger_enabled");
        boolean fall_damage_enabled = plugin.getConfig().getBoolean("server.fall_damage_enabled");
        boolean dev = plugin.getConfig().getBoolean("server.dev");
        boolean owner = plugin.getConfig().getBoolean("players.owner");
        boolean superusers = plugin.getConfig().getBoolean("players.superusers");
        Nitrogen.BuildProperties build = Nitrogen.build;
        if (args.length == 0)
        {
            sender.sendMessage(ChatColor.GOLD + String.format("Version "
                            + ChatColor.BLUE + "%s.%s.%s",
                    build.version,
                    build.number,
                    build.head));
            sender.sendMessage(String.format(ChatColor.GOLD + "Compiled "
                            + ChatColor.BLUE + "%s" + ChatColor.GOLD + " by "
                            + ChatColor.BLUE + "%s",
                    build.date,
                    build.author));
            sender.sendMessage(ChatColor.GOLD + "Designed for: " + ChatColor.BLUE + "Minecraft 1.14");
            sender.sendMessage(ChatColor.RED + "Nitrogen is an advanced plugin designed to provide useful utilities for a Minecraft server");
            sender.sendMessage(ChatColor.GREEN + "Type /contributors to see who contributed to Nitrogen");
            sender.sendMessage(ChatColor.GREEN + "Type /nitrogen reload to reload the configuration file");
            if (dev)
            {
                sender.sendMessage(ChatColor.DARK_AQUA + "The server is currently in development mode. "
                        + "This means there may be unstable plugin builds on this server, and the server could crash more than normal!");
            }
            return true;
        }
        switch (args[0].toLowerCase())
        {
            case "reload":
            {
                if (!sender.hasPermission("nitrogen.reload"))
                {
                    sender.sendMessage(Messages.NO_PERMISSION);
                    return true;
                }
                plugin.reloadConfig();
                sender.sendMessage(ChatColor.GRAY + "Configuration file has been successfully reloaded!");
                return true;
            }
            case "debug":
            {
                if (!sender.hasPermission("nitrogen.debug"))
                {
                    sender.sendMessage(Messages.NO_PERMISSION);
                    return true;
                }
                sender.sendMessage(ChatColor.GRAY + "Config Options:");
                sender.sendMessage(ChatColor.GRAY + "server.spawn_on_join: " + spawn_on_join);
                sender.sendMessage(ChatColor.GRAY + "server.applications_enabled: " + applications_enabled);
                sender.sendMessage(ChatColor.GRAY + "server.dev: " + dev);
                sender.sendMessage(ChatColor.GRAY + "server.drop_items_on_death: " + drop_items_on_death);
                sender.sendMessage(ChatColor.GRAY + "server.clear_inventory_on_join: " + clear_inventory_on_join);
                sender.sendMessage(ChatColor.GRAY + "server.hunger_enabled: " + server_hunger);
                sender.sendMessage(ChatColor.GRAY + "server.fall_damage_enabled: " + fall_damage_enabled);
                sender.sendMessage(ChatColor.GRAY + "players.owner: " + owner);
                sender.sendMessage(ChatColor.GRAY + "players.superusers: " + superusers);
                sender.sendMessage(ChatColor.GRAY + "Nitrogen.build.formattedVersion(): " + Nitrogen.build.formattedVersion());
                sender.sendMessage(ChatColor.GRAY + "COMPILE_NMS_VERSION: " + Nitrogen.COMPILE_NMS_VERSION);
                return true;
            } // debug end
            case "help":
            {
                sender.sendMessage(ChatColor.GRAY + "To see Nitrogen's help, type /help Nitrogen or /? Nitrogen. If you are the owner, for additional inforrmation, please read the help.yml file inside of the Nitrogen folder.");
                return true;
            }
            default:
            {
                return false;
            }
        } // end args
    }
}

