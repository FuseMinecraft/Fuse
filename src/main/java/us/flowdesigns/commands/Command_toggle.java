package us.flowdesigns.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(source = SourceType.BOTH)
@CommandParameters(description = "Toggles various configuration options", usage = "/<command>")
public class Command_toggle extends BaseCommand {
    @Override
    public boolean run(final CommandSender sender, final Player sender_p, final Command cmd, final String commandLabel, final String[] args, final boolean senderIsConsole) {
        String spawn_on_join = plugin.getConfig().getString("server.spawn_on_join");
        String drop_items_on_death = plugin.getConfig().getString("server.drop_items_on_death");
        String clear_inventory_on_join = plugin.getConfig().getString("server.clear_inventory_on_join");
        String hunger_enabled = plugin.getConfig().getString("server.hunger_enabled");
        String weather_enabled = plugin.getConfig().getString("server.weather_enabled");
        String fall_damage_enabled = plugin.getConfig().getString("server.fall_damage_enabled");
        String splash_potions_enabled = plugin.getConfig().getString("server.splash_potions_enabled");
        String fun_cmds = plugin.getConfig().getString("commands.fun_commands");
        String login_messages = plugin.getConfig().getString("server.login_messages_enabled");
        String applications_enabled = plugin.getConfig().getString("commands.applications_enabled");
        if (!sender.hasPermission("fuse.toggle"))
        {
            sender.sendMessage(Messages.MSG_NO_PERMS);
            return true;
        }
        if (args.length == 0) {
            sender.sendMessage(ChatColor.GRAY + "Configuration Toggles:");
            sender.sendMessage(ChatColor.GRAY + "Spawn on Join (spawn_on_join): " + spawn_on_join);
            sender.sendMessage(ChatColor.GRAY + "Drop Items on Death (drop_items_on_death): " + drop_items_on_death);
            sender.sendMessage(ChatColor.GRAY + "Clear Inventory on Join (clear_inventory_on_join): " + clear_inventory_on_join);
            sender.sendMessage(ChatColor.GRAY + "Hunger Enabled (hunger_enabled): " + hunger_enabled);
            sender.sendMessage(ChatColor.GRAY + "Weather Enabled (weather_enabled): " + weather_enabled);
            sender.sendMessage(ChatColor.GRAY + "Fall Damage Enabled (fall_damage_enabled): " + fall_damage_enabled);
            sender.sendMessage(ChatColor.GRAY + "Splash Potions Enabled (splash_potions_enabled): " + splash_potions_enabled);
            sender.sendMessage(ChatColor.GRAY + "Fun Commands Enabled (fun_commands): " + fun_cmds);
            sender.sendMessage(ChatColor.GRAY + "Login Messages Enabled (login_messages): " + login_messages);
            sender.sendMessage(ChatColor.GRAY + "Admin Applications Enabled (applications_enabled): " + applications_enabled);
            return true;
        }
        // Spawn on join
        if (args[0].equalsIgnoreCase("spawn_on_join")) {
            if (spawn_on_join.equalsIgnoreCase("true")) {
                plugin.getConfig().set("server.spawn_on_join", "false");
                plugin.saveConfig();
                plugin.reloadConfig();
                sender.sendMessage(ChatColor.GRAY + "People will no longer spawn when they join");
                return true;
            }
            if (spawn_on_join.equalsIgnoreCase("false")) {
                plugin.getConfig().set("server.spawn_on_join", "true");
                plugin.saveConfig();
                plugin.reloadConfig();
                sender.sendMessage(ChatColor.GRAY + "People will now spawn when they join");
                return true;
            }
        }
        // Drop items on death
        if (args[0].equalsIgnoreCase("drop_items_on_death")) {
            if (drop_items_on_death.equalsIgnoreCase("true")) {
                plugin.getConfig().set("server.drop_items_on_death", "false");
                plugin.saveConfig();
                plugin.reloadConfig();
                sender.sendMessage(ChatColor.GRAY + "Items will not be dropped on death");
                return true;
            }
            if (drop_items_on_death.equalsIgnoreCase("false")) {
                plugin.getConfig().set("server.drop_items_on_death", "true");
                plugin.saveConfig();
                plugin.reloadConfig();
                sender.sendMessage(ChatColor.GRAY + "Items will now be dropped on death");
                return true;
                }
            }
            // Clear inventory on join
            if (args[0].equalsIgnoreCase("clear_inventory_on_join")) {
                if (clear_inventory_on_join.equalsIgnoreCase("true")) {
                    plugin.getConfig().set("server.clear_inventory_on_join", "false");
                    plugin.saveConfig();
                    plugin.reloadConfig();
                    sender.sendMessage(ChatColor.GRAY + "Players inventories will not be cleared on join");
                    return true;
                }
                if (clear_inventory_on_join.equalsIgnoreCase("false")) {
                    plugin.getConfig().set("server.clear_inventory_on_join", "true");
                    plugin.saveConfig();
                    plugin.reloadConfig();
                    sender.sendMessage(ChatColor.GRAY + "Players inventories will now be cleared when they join");
                    return true;
                }
            }
            // Hunger enabled
            if (args[0].equalsIgnoreCase("hunger_enabled")) {
                if (hunger_enabled.equalsIgnoreCase("true")) {
                    plugin.getConfig().set("server.hunger_enabled", "false");
                    plugin.saveConfig();
                    plugin.reloadConfig();
                    sender.sendMessage(ChatColor.GRAY + "Hunger is now disabled");
                    return true;
                }
                if (hunger_enabled.equalsIgnoreCase("false")) {
                    plugin.getConfig().set("server.hunger_enabled", "true");
                    plugin.saveConfig();
                    plugin.reloadConfig();
                    sender.sendMessage(ChatColor.GRAY + "Hunger is now enabled");
                    return true;
                }
            }
        // Weather enabled
        if (args[0].equalsIgnoreCase("weather_enabled")) {
            if (weather_enabled.equalsIgnoreCase("true")) {
                plugin.getConfig().set("server.weather_enabled", "false");
                plugin.saveConfig();
                plugin.reloadConfig();
                sender.sendMessage(ChatColor.GRAY + "Weather is now disabled");
                return true;
            }
            if (weather_enabled.equalsIgnoreCase("false")) {
                plugin.getConfig().set("server.weather_enabled", "true");
                plugin.saveConfig();
                plugin.reloadConfig();
                sender.sendMessage(ChatColor.GRAY + "Weather is now enabled");
                return true;
            }
        }
        // Fall damage enabled
        if (args[0].equalsIgnoreCase("fall_damage_enabled")) {
            if (fall_damage_enabled.equalsIgnoreCase("true")) {
                plugin.getConfig().set("server.fall_damage_enabled", "false");
                plugin.saveConfig();
                plugin.reloadConfig();
                sender.sendMessage(ChatColor.GRAY + "Players will no longer take fall damage");
                return true;
            }
            if (fall_damage_enabled.equalsIgnoreCase("false")) {
                plugin.getConfig().set("server.fall_damage_enabled", "true");
                plugin.saveConfig();
                plugin.reloadConfig();
                sender.sendMessage(ChatColor.GRAY + "Players will now take fall damage");
                return true;
            }
        }
        // Splash potions enabled
        if (args[0].equalsIgnoreCase("splash_potions_enabled")) {
            if (splash_potions_enabled.equalsIgnoreCase("true")) {
                plugin.getConfig().set("server.splash_potions_enabled", "false");
                plugin.saveConfig();
                plugin.reloadConfig();
                sender.sendMessage(ChatColor.GRAY + "Players can no longer use splash potions");
                return true;
            }
            if (splash_potions_enabled.equalsIgnoreCase("false")) {
                plugin.getConfig().set("server.splash_potions_enabled", "true");
                plugin.saveConfig();
                plugin.reloadConfig();
                sender.sendMessage(ChatColor.GRAY + "Players can now use splash potions");
                return true;
            }
        }
        // Fun commands enabled
        if (args[0].equalsIgnoreCase("fun_commands")) {
            if (fun_cmds.equalsIgnoreCase("enabled")) {
                plugin.getConfig().set("commands.fun_commands", "disabled");
                plugin.saveConfig();
                plugin.reloadConfig();
                sender.sendMessage(ChatColor.GRAY + "Players can no longer use fun commands");
                return true;
            }
            if (fun_cmds.equalsIgnoreCase("disabled")) {
                plugin.getConfig().set("commands.fun_commands", "enabled");
                plugin.saveConfig();
                plugin.reloadConfig();
                sender.sendMessage(ChatColor.GRAY + "Players can now use fun commands");
                return true;
            }
        }
        // Login messages
        if (args[0].equalsIgnoreCase("login_messages")) {
            if (login_messages.equalsIgnoreCase("true")) {
                plugin.getConfig().set("server.login_messages_enabled", "false");
                plugin.saveConfig();
                plugin.reloadConfig();
                sender.sendMessage(ChatColor.GRAY + "Login messages are now disabled");
                return true;
            }
            if (login_messages.equalsIgnoreCase("false")) {
                plugin.getConfig().set("server.login_messages_enabled", "true");
                plugin.saveConfig();
                plugin.reloadConfig();
                sender.sendMessage(ChatColor.GRAY + "Login messages are now enabled");
                return true;
            }
        }
        // Admin applications enabled
        if (args[0].equalsIgnoreCase("applications_enabled")) {
            if (applications_enabled.equalsIgnoreCase("true")) {
                plugin.getConfig().set("commands.applications_enabled", "false");
                plugin.saveConfig();
                plugin.reloadConfig();
                sender.sendMessage(ChatColor.GRAY + "Players can no longer access the /admininfo command");
                return true;
            }
            if (applications_enabled.equalsIgnoreCase("false")) {
                plugin.getConfig().set("commands.applications_enabled", "true");
                plugin.saveConfig();
                plugin.reloadConfig();
                sender.sendMessage(ChatColor.GRAY + "Players can now use the /admininfo command");
                sender.sendMessage(ChatColor.RED + "Be sure that the configuration fields are filled out, or else the command will not work.");
                return true;
            }
        }
            return true;
    }
}
