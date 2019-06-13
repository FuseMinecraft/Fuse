package me.telesphoreo.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(source = SourceType.BOTH)
@CommandParameters(description = "Toggles various configuration options", usage = "/<command>")
public class Command_toggle extends BaseCommand
{
    @Override
    public boolean run(final CommandSender sender, final Player sender_p, final Command cmd, final String commandLabel, final String[] args, final boolean senderIsConsole)
    {
        boolean spawn_on_join = plugin.getConfig().getBoolean("server.spawn_on_join");
        boolean drop_items_on_death = plugin.getConfig().getBoolean("server.drop_items_on_death");
        boolean clear_inventory_on_join = plugin.getConfig().getBoolean("server.clear_inventory_on_join");
        boolean hunger_enabled = plugin.getConfig().getBoolean("server.hunger_enabled");
        boolean weather_enabled = plugin.getConfig().getBoolean("server.weather_enabled");
        boolean fall_damage_enabled = plugin.getConfig().getBoolean("server.fall_damage_enabled");
        boolean splash_potions_enabled = plugin.getConfig().getBoolean("server.splash_potions_enabled");
        boolean fun_cmds = plugin.getConfig().getBoolean("commands.fun_commands");
        boolean login_messages = plugin.getConfig().getBoolean("server.login_messages_enabled");
        boolean applications_enabled = plugin.getConfig().getBoolean("commands.applications_enabled");
        if (!sender.hasPermission("nitrogen.toggle"))
        {
            sender.sendMessage(Messages.NO_PERMISSION);
            return true;
        }
        if (args.length == 0)
        {
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
        if (args[0].equalsIgnoreCase("spawn_on_join"))
        {
            if (spawn_on_join)
            {
                plugin.getConfig().set("server.spawn_on_join", false);
                plugin.saveConfig();
                plugin.reloadConfig();
                sender.sendMessage(ChatColor.GRAY + "People will no longer spawn when they join");
                return true;
            }
            if (!spawn_on_join)
            {
                plugin.getConfig().set("server.spawn_on_join", true);
                plugin.saveConfig();
                plugin.reloadConfig();
                sender.sendMessage(ChatColor.GRAY + "People will now spawn when they join");
                return true;
            }
        }
        // Drop items on death
        if (args[0].equalsIgnoreCase("drop_items_on_death"))
        {
            if (drop_items_on_death)
            {
                plugin.getConfig().set("server.drop_items_on_death", false);
                plugin.saveConfig();
                plugin.reloadConfig();
                sender.sendMessage(ChatColor.GRAY + "Items will not be dropped on death");
                return true;
            }
            if (!drop_items_on_death)
            {
                plugin.getConfig().set("server.drop_items_on_death", true);
                plugin.saveConfig();
                plugin.reloadConfig();
                sender.sendMessage(ChatColor.GRAY + "Items will now be dropped on death");
                return true;
            }
        }
        // Clear inventory on join
        if (args[0].equalsIgnoreCase("clear_inventory_on_join"))
        {
            if (clear_inventory_on_join)
            {
                plugin.getConfig().set("server.clear_inventory_on_join", false);
                plugin.saveConfig();
                plugin.reloadConfig();
                sender.sendMessage(ChatColor.GRAY + "Players inventories will not be cleared on join");
                return true;
            }
            if (!clear_inventory_on_join)
            {
                plugin.getConfig().set("server.clear_inventory_on_join", true);
                plugin.saveConfig();
                plugin.reloadConfig();
                sender.sendMessage(ChatColor.GRAY + "Players inventories will now be cleared when they join");
                return true;
            }
        }
        // Hunger enabled
        if (args[0].equalsIgnoreCase("hunger_enabled"))
        {
            if (hunger_enabled)
            {
                plugin.getConfig().set("server.hunger_enabled", false);
                plugin.saveConfig();
                plugin.reloadConfig();
                sender.sendMessage(ChatColor.GRAY + "Hunger is now disabled");
                return true;
            }
            if (!hunger_enabled)
            {
                plugin.getConfig().set("server.hunger_enabled", true);
                plugin.saveConfig();
                plugin.reloadConfig();
                sender.sendMessage(ChatColor.GRAY + "Hunger is now enabled");
                return true;
            }
        }
        // Weather enabled
        if (args[0].equalsIgnoreCase("weather_enabled"))
        {
            if (weather_enabled)
            {
                plugin.getConfig().set("server.weather_enabled", false);
                plugin.saveConfig();
                plugin.reloadConfig();
                sender.sendMessage(ChatColor.GRAY + "Weather is now disabled");
                return true;
            }
            if (!weather_enabled)
            {
                plugin.getConfig().set("server.weather_enabled", true);
                plugin.saveConfig();
                plugin.reloadConfig();
                sender.sendMessage(ChatColor.GRAY + "Weather is now enabled");
                return true;
            }
        }
        // Fall damage enabled
        if (args[0].equalsIgnoreCase("fall_damage_enabled"))
        {
            if (fall_damage_enabled)
            {
                plugin.getConfig().set("server.fall_damage_enabled", false);
                plugin.saveConfig();
                plugin.reloadConfig();
                sender.sendMessage(ChatColor.GRAY + "Players will no longer take fall damage");
                return true;
            }
            if (!fall_damage_enabled)
            {
                plugin.getConfig().set("server.fall_damage_enabled", true);
                plugin.saveConfig();
                plugin.reloadConfig();
                sender.sendMessage(ChatColor.GRAY + "Players will now take fall damage");
                return true;
            }
        }
        // Splash potions enabled
        if (args[0].equalsIgnoreCase("splash_potions_enabled"))
        {
            if (splash_potions_enabled)
            {
                plugin.getConfig().set("server.splash_potions_enabled", false);
                plugin.saveConfig();
                plugin.reloadConfig();
                sender.sendMessage(ChatColor.GRAY + "Players can no longer use splash potions");
                return true;
            }
            if (!splash_potions_enabled)
            {
                plugin.getConfig().set("server.splash_potions_enabled", true);
                plugin.saveConfig();
                plugin.reloadConfig();
                sender.sendMessage(ChatColor.GRAY + "Players can now use splash potions");
                return true;
            }
        }
        // Fun commands enabled
        if (args[0].equalsIgnoreCase("fun_commands"))
        {
            if (fun_cmds)
            {
                plugin.getConfig().set("commands.fun_commands", false);
                plugin.saveConfig();
                plugin.reloadConfig();
                sender.sendMessage(ChatColor.GRAY + "Players can no longer use fun commands");
                return true;
            }
            if (!fun_cmds)
            {
                plugin.getConfig().set("commands.fun_commands", true);
                plugin.saveConfig();
                plugin.reloadConfig();
                sender.sendMessage(ChatColor.GRAY + "Players can now use fun commands");
                return true;
            }
        }
        // Login messages
        if (args[0].equalsIgnoreCase("login_messages"))
        {
            if (login_messages)
            {
                plugin.getConfig().set("server.login_messages_enabled", false);
                plugin.saveConfig();
                plugin.reloadConfig();
                sender.sendMessage(ChatColor.GRAY + "Login messages are now disabled");
                return true;
            }
            if (!login_messages)
            {
                plugin.getConfig().set("server.login_messages_enabled", true);
                plugin.saveConfig();
                plugin.reloadConfig();
                sender.sendMessage(ChatColor.GRAY + "Login messages are now enabled");
                return true;
            }
        }
        // Admin applications enabled
        if (args[0].equalsIgnoreCase("applications_enabled"))
        {
            if (applications_enabled)
            {
                plugin.getConfig().set("commands.applications_enabled", false);
                plugin.saveConfig();
                plugin.reloadConfig();
                sender.sendMessage(ChatColor.GRAY + "Players can no longer access the /admininfo command");
                return true;
            }
            if (!applications_enabled)
            {
                plugin.getConfig().set("commands.applications_enabled", true);
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
