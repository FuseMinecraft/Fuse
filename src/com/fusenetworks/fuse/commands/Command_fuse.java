package com.fusenetworks.fuse.commands;

import com.fusenetworks.fuse.Fuse;
import static org.bukkit.Bukkit.getServer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;

@CommandPermissions(source = SourceType.BOTH)
@CommandParameters(description = "Shows information about Fuse", usage = "/<command> [reload | debug | help]", aliases = "packscore,oxygen,sky,nitrogen,trident")
public class Command_fuse extends BaseCommand {
    @Override
    public boolean run(final CommandSender sender, final Player sender_p, final Command cmd, final String commandLabel, final String[] args, final boolean senderIsConsole) 
    {
        String spawn_on_join = plugin.getConfig().getString("server.spawn_on_join");
        String applications_enabled = plugin.getConfig().getString("server.applications_enabled");
        String op_kits = plugin.getConfig().getString("server.op_kits");
        String location = plugin.getConfig().getString("server.location");
        String drop_items_on_death = plugin.getConfig().getString("server.drop_items_on_death");
        String clear_inventory_on_join = plugin.getConfig().getString("server.clear_inventory_on_join");
        String server_hunger = plugin.getConfig().getString("server.hunger_enabled");
        String fall_damage_enabled = plugin.getConfig().getString("server.fall_damage_enabled");
        String dev = plugin.getConfig().getString("server.dev");
        PluginManager pm = getServer().getPluginManager();
        Plugin p = pm.getPlugin("Fuse");
        PluginDescriptionFile pdf = p.getDescription();
        String version = pdf.getVersion();
        if (args.length == 0)
        {
        sender.sendMessage(ChatColor.GOLD + "" + plugin.getName() + "/" + version);
        sender.sendMessage(ChatColor.RED + "Fuse is an advanced plugin designed to provide usefull utilities for a Minecraft server");
        sender.sendMessage(ChatColor.RED + "Compiled on " + Fuse.buildDate + " by " + Fuse.buildCreator);
        sender.sendMessage(ChatColor.GREEN + "Type /fuse help for command usage");
        sender.sendMessage(ChatColor.GREEN + "Type /contributors to see who contributed to Fuse");
        if (dev.equals("true"))
        {
        sender.sendMessage(ChatColor.DARK_AQUA + "Fuse Networks is currently in development mode. "
        + "This means there may be unstable plugin builds on this server, and the server could crash more than normal!");
        }
        return true;
        }
        switch (args[0].toLowerCase()) {
            case "reload":
            {
                if (!sender.hasPermission("fuse.reload"))
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
                if (!sender.hasPermission("fuse.debug"))
                {
                    sender.sendMessage(Messages.MSG_NO_PERMS);
                    return true;
                }
                sender.sendMessage(ChatColor.GRAY + "Config Options");
                sender.sendMessage(ChatColor.GRAY + "server.spawn_on_join: " + spawn_on_join);
                sender.sendMessage(ChatColor.GRAY + "server.applications_enabled: " + applications_enabled);
                sender.sendMessage(ChatColor.GRAY + "server.op_kits: " + op_kits);
                sender.sendMessage(ChatColor.GRAY + "server.dev: " + dev);
                sender.sendMessage(ChatColor.GRAY + "server.location: " + location);
                sender.sendMessage(ChatColor.GRAY + "server.drop_items_on_death: " + drop_items_on_death);
                sender.sendMessage(ChatColor.GRAY + "server.clear_inventory_on_join: " + clear_inventory_on_join);
                sender.sendMessage(ChatColor.GRAY + "server.hunger_enabled: " + server_hunger);
                sender.sendMessage(ChatColor.GRAY + "server.fall_damage_enabled: " + fall_damage_enabled);
                return true;
        } // debug end
            case "help":
            {
                if (!sender.hasPermission("fuse.extendedhelp"))
                {
                    sender.sendMessage(ChatColor.GOLD + "Fuse Help");
                    sender.sendMessage(ChatColor.GOLD + "/admininfo - Tells you how to apply for admin");
                    sender.sendMessage(ChatColor.GOLD + "/contributors - Lists Fuse Networks's contributors");
                    sender.sendMessage(ChatColor.GOLD + "/discord - Gives a link to the Discord server");
                    sender.sendMessage(ChatColor.GOLD + "/forums - Gives a link to the forums");
                    sender.sendMessage(ChatColor.GOLD + "/fw - Shoots a firework into the sky");
                    sender.sendMessage(ChatColor.GOLD + "/glassmode - Makes you invisible and glowing");
                    sender.sendMessage(ChatColor.GOLD + "/information - Gives you information about the server you're currently on");
                    sender.sendMessage(ChatColor.GOLD + "/rename - Renames the item in your hand");
                    sender.sendMessage(ChatColor.GOLD + "/staff - Lists all the staff");
                    sender.sendMessage(ChatColor.GOLD + "/" + plugin.getName().toLowerCase() + " - Shows information about " + plugin.getName());
                    sender.sendMessage(ChatColor.GOLD + "/information - Gives you information about the server you're currently on");
                    sender.sendMessage(ChatColor.GOLD + "/website - Gives you a link to the website");
                    return true;
                } else {
                    sender.sendMessage(ChatColor.GOLD + "Fuse Help");
                    sender.sendMessage(ChatColor.RED + "/adminchat - Talk privatley with other admins");
                    sender.sendMessage(ChatColor.GOLD + "/admininfo - Tells you how to apply for admin");
                    sender.sendMessage(ChatColor.RED + "/clearlag [-s] - Clears lag");
                    sender.sendMessage(ChatColor.RED + "/commandspy - Toggles commandspy");
                    sender.sendMessage(ChatColor.GOLD + "/contributors - Lists Fuse Networks's contributors");
                    sender.sendMessage(ChatColor.GOLD + "/discord - Gives a link to the Discord server");
                    sender.sendMessage(ChatColor.RED + "/expel - Push players away from you");
                    sender.sendMessage(ChatColor.GOLD + "/forums - Gives a link to the forums");
                    sender.sendMessage(ChatColor.GOLD + "/fw - Shoots a firework into the sky");
                    sender.sendMessage(ChatColor.RED + "/gcmd - Makes another player execute a command");
                    sender.sendMessage(ChatColor.GOLD + "/glassmode - Makes you invisible and glowing");
                    sender.sendMessage(ChatColor.GOLD + "/information - Gives you information about the server you're currently on");
                    sender.sendMessage(ChatColor.GOLD + "/rename - Renames the item in your hand");
                    sender.sendMessage(ChatColor.RED + "/ship - Ship a player with another player");
                    sender.sendMessage(ChatColor.GOLD + "/staff - Lists all the staff");
                    sender.sendMessage(ChatColor.GOLD + "/" + plugin.getName().toLowerCase() + " - Shows information about " + plugin.getName());
                    sender.sendMessage(ChatColor.RED + "/unloadchunks [-s] - Unloads all unused chunks");
                    sender.sendMessage(ChatColor.GOLD + "/website - Gives you a link to the website");
                    return true;
                }
            }
            default:
            {
                return false;
            }
        } // end args
    }
}

