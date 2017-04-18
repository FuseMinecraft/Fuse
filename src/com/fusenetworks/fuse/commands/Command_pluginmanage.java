package com.fusenetworks.fuse.commands;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

// Credit to TF

@CommandPermissions(source = SourceType.BOTH)
@CommandParameters( description = "Manage plugins", usage = "/<command> <<enable | disable | reload> <pluginname>> | list>", aliases = "plc,pm")
public class Command_pluginmanage extends BaseCommand {
    @Override
    public boolean run(final CommandSender sender, final Player sender_p, final Command cmd, final String commandLabel, final String[] args, final boolean senderIsConsole) {
    {
        if (!sender.hasPermission("fuse.pluginmanage"))
        {
            sender.sendMessage(Messages.MSG_NO_PERMS);
            return true;
        }
        if (args.length == 0 || args.length > 2)
        {
            return false;
        }

        final PluginManager pm = server.getPluginManager();

        if (args.length == 1)
        {
            if (args[0].equalsIgnoreCase("list"))
            {
                for (Plugin serverPlugin : pm.getPlugins())
                {
                    final String version = serverPlugin.getDescription().getVersion();
                    sender.sendMessage(ChatColor.GRAY + "- " + (serverPlugin.isEnabled() ? ChatColor.GREEN : ChatColor.RED) + serverPlugin.getName()
                            + ChatColor.GOLD + (version != null && !version.isEmpty() ? " v" + version : "") + " by "
                            + StringUtils.join(serverPlugin.getDescription().getAuthors(), ", "));
                }

                return true;
            }

            return false;
        }

        if ("enable".equals(args[0]))
        {
            final Plugin target = getPlugin(args[1]);
            if (target == null)
            {
                sender.sendMessage(ChatColor.GRAY + "Plugin not found!");
                return true;
            }

            if (target.isEnabled())
            {
                sender.sendMessage(ChatColor.GRAY + "Plugin is already enabled.");
                return true;
            }

            pm.enablePlugin(target);

            if (!pm.isPluginEnabled(target))
            {
                sender.sendMessage(ChatColor.GRAY + "Error enabling plugin " + target.getName());
                return true;
            }

            sender.sendMessage(ChatColor.GRAY + target.getName() + " is now enabled.");
            return true;
        }

        if ("disable".equals(args[0]))
        {
            final Plugin target = getPlugin(args[1]);
            if (target == null)
            {
                sender.sendMessage(ChatColor.GRAY + "Plugin not found!");
                return true;
            }

            if (!target.isEnabled())
            {
                sender.sendMessage(ChatColor.GRAY + "Plugin is already disabled.");
                return true;
            }

            if (target.getName().equals(plugin.getName()))
            {
                sender.sendMessage(ChatColor.GRAY + "You cannot disable " + plugin.getName());
                return true;
            }
            
            if (target.getName().equals("ViaVersion"))
            {
                sender.sendMessage(ChatColor.RED + "You cannot disable ViaVersion");
                return true;
            }

            pm.disablePlugin(target);

            if (pm.isPluginEnabled(target))
            {
                sender.sendMessage(ChatColor.GRAY + "Error disabling plugin " + target.getName());
                return true;
            }

            sender.sendMessage(ChatColor.GRAY + target.getName() + " is now disabled.");
            return true;
        }

        if ("reload".equals(args[0]))
        {
            final Plugin target = getPlugin(args[1]);
            if (target == null)
            {
                sender.sendMessage(ChatColor.GRAY + "Plugin not found!");
                return true;
            }
            
            if (target.getName().equals("ViaVersion"))
            {
                sender.sendMessage(ChatColor.RED + "You cannot reload ViaVersion");
                return true;
            }

            pm.disablePlugin(target);
            pm.enablePlugin(target);
            sender.sendMessage(ChatColor.GRAY + target.getName() + " reloaded.");
            return true;
        }

        return false;
    }
}
    public Plugin getPlugin(String name)
    {
        for (Plugin serverPlugin : server.getPluginManager().getPlugins())
        {
            if (serverPlugin.getName().equalsIgnoreCase(name))
            {
                return serverPlugin;
            }
        }

        if (name.length() >= 3)
        {
            for (Plugin serverPlugin : server.getPluginManager().getPlugins())
            {
                if (serverPlugin.getName().toLowerCase().contains(name.toLowerCase()))
                {
                    return serverPlugin;
                }
            }
        }

        return null;
    }
}