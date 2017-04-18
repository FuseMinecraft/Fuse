/*

Remove later

package com.fusenetworks.fuse.commands;

import com.fusenetworks.fuse.util.NUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(source = SourceType.BOTH)
@CommandParameters(description = "Manages ranks", usage = "/<command> <add | remove> <player> <rank>")
public class Command_manage extends BaseCommand {
    @Override
    public boolean run(final CommandSender sender, final Player sender_p, final Command cmd, final String commandLabel, final String[] args, final boolean senderIsConsole) {
        String superuers = plugin.getConfig().getString("players.superusers");

        if (!superuers.contains(sender.getName()))
        {
            sender.sendMessage(Messages.MSG_NO_PERMS);
        }

        if (args.length != 3) {
            return false;
        }

        boolean doneAction = false;
        Player player = getPlayer(args[1]);

        if (player == null) {
            sender.sendMessage(Messages.PLAYER_NOT_FOUND);
            return true;
        }

        String name = player.getName();
        String rank = args[2].toLowerCase().substring(0, 1).toUpperCase() + args[2].toLowerCase().substring(1);

        if (args[0].equalsIgnoreCase("add")) {
            switch (args[2].toLowerCase()) {
                case "member":
                    server.dispatchCommand(Bukkit.getConsoleSender(), "manuadd " + name + " member");
                    doneAction = true;
                    break;
                case "moderator":
                case "mod":
                    server.dispatchCommand(Bukkit.getConsoleSender(), "manuadd " + name + " moderator");
                    doneAction = true;
                    break;
                case "administrator":
                case "admin":
                    server.dispatchCommand(Bukkit.getConsoleSender(), "manuadd " + name + " admin");
                    doneAction = true;
                    break;
                case "builder":
                    server.dispatchCommand(Bukkit.getConsoleSender(), "manuadd " + name + " builder");
                    doneAction = true;
                    break;
                case "developer":
                case "dev":
                    server.dispatchCommand(Bukkit.getConsoleSender(), "manuadd " + name + " developer");
                    doneAction = true;
                    break;
            }
            if (!doneAction) {
                sender.sendMessage(ChatColor.RED + "That rank is invalid.");
                return true;
            }
            sender.sendMessage(ChatColor.GOLD + plugin.getName() + " >> Player " + name + " added to " + rank);
            NUtil.adminAction(sender.getName(), "Adding " + name + " to " + rank, false);
        }

        if (args[0].equalsIgnoreCase("remove")) {
            switch (args[2].toLowerCase()) {
                case "moderator":
                case "mod":
                    server.dispatchCommand(Bukkit.getConsoleSender(), "manudel " + name);
                    doneAction = true;
                    break;
                case "administrator":
                case "admin":
                    server.dispatchCommand(Bukkit.getConsoleSender(), "manudel " + name);
                    doneAction = true;
                    break;
                case "builder":
                    server.dispatchCommand(Bukkit.getConsoleSender(), "manudel " + name);
                    doneAction = true;
                    break;
                case "developer":
                case "dev":
                    server.dispatchCommand(Bukkit.getConsoleSender(), "manudel " + name);
                    doneAction = true;
                    break;
            }
            if (!doneAction) {
                sender.sendMessage(ChatColor.RED + "That rank is invalid.");
                return true;
            }
            sender.sendMessage(ChatColor.GOLD + plugin.getName() + " >> Player " + player.getName() + " removed from " + rank);
            NUtil.adminAction(sender.getName(), "Removed player " + player.getName() + " from " + rank, false);
        }
        return true;
    }
}
*/