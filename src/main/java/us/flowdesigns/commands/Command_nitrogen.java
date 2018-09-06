package us.flowdesigns.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import us.flowdesigns.nitrogen.Nitrogen;
import us.flowdesigns.nitrogen.Updater;

@CommandPermissions(source = SourceType.BOTH)
@CommandParameters(description = "Shows information about Nitrogen", usage = "/<command> [reload | debug | help | update]")
public class Command_nitrogen extends BaseCommand
{
    @Override
    public boolean run(final CommandSender sender, final Player sender_p, final Command cmd, final String commandLabel, final String[] args, final boolean senderIsConsole)
    {
        String spawn_on_join = plugin.getConfig().getString("server.spawn_on_join");
        String applications_enabled = plugin.getConfig().getString("commands.applications_enabled");
        String drop_items_on_death = plugin.getConfig().getString("server.drop_items_on_death");
        String clear_inventory_on_join = plugin.getConfig().getString("server.clear_inventory_on_join");
        String server_hunger = plugin.getConfig().getString("server.hunger_enabled");
        String fall_damage_enabled = plugin.getConfig().getString("server.fall_damage_enabled");
        String dev = plugin.getConfig().getString("server.dev");
        String owner = plugin.getConfig().getString("players.owner");
        String superusers = plugin.getConfig().getString("players.superusers");
        Nitrogen.BuildProperties build = Nitrogen.build;
        if (args.length == 0)
        {
            sender.sendMessage(ChatColor.GOLD + String.format("Version "
                            + ChatColor.BLUE + "%s - %s Build %s " + ChatColor.GOLD + "("
                            + ChatColor.BLUE + "%s" + ChatColor.GOLD + ")",
                    build.codename,
                    build.version,
                    build.number,
                    build.head));
            sender.sendMessage(String.format(ChatColor.GOLD + "Compiled "
                            + ChatColor.BLUE + "%s" + ChatColor.GOLD + " by "
                            + ChatColor.BLUE + "%s",
                    build.date,
                    build.author));
            sender.sendMessage(ChatColor.GOLD + "Designed for: " + ChatColor.BLUE + "Spigot 1.13.1");
            sender.sendMessage(ChatColor.RED + "Nitrogen is an advanced plugin designed to provide useful utilities for a Minecraft server");
            sender.sendMessage(ChatColor.GREEN + "Type /contributors to see who contributed to Nitrogen");
            sender.sendMessage(ChatColor.GREEN + "Type /nitrogen reload to reload the configuration file");
            sender.sendMessage(ChatColor.GREEN + "Type /nitrogen update to check for and install updates");
            if (dev.equalsIgnoreCase("true"))
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
                    sender.sendMessage(Messages.MSG_NO_PERMS);
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
                    sender.sendMessage(Messages.MSG_NO_PERMS);
                    return true;
                }
                sender.sendMessage(ChatColor.GRAY + "Config Options");
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
            case "update":
            {
                if (!sender.hasPermission("nitrogen.update"))
                {
                    sender.sendMessage(Messages.MSG_NO_PERMS);
                    return true;
                }
                Updater updater = new Updater(Nitrogen.plugin);
                updater.update(sender);
                return true;
            }
            default:
            {
                return false;
            }
        } // end args
    }
}

