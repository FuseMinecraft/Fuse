package me.telesphoreo.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(source = SourceType.BOTH)
@CommandParameters(description = "Ships a player with another player", usage = "/<command> <player1> <player2>")
public class Command_ship extends BaseCommand
{
    @Override
    public boolean run(final CommandSender sender, final Player sender_p, final Command cmd, final String commandLabel, final String[] args, final boolean senderIsConsole)
    {
        boolean fun = plugin.getConfig().getBoolean("commands.fun_commands");
        if (!fun)
        {
            sender.sendMessage(Messages.UNKNOWN_COMMAND);
            return true;
        }
        if (!sender.hasPermission("nitrogen.ship"))
        {
            sender.sendMessage(Messages.NO_PERMISSION);
            return true;
        }
        if (args.length != 2)
        {
            return false;
        }

        String notPacks = args[0];
        String definitelyNotLemonHesForeverAlone /* probably still true */ = args[1];

        if (notPacks.equals(definitelyNotLemonHesForeverAlone))
        {
            sender.sendMessage(ChatColor.RED + "You can't ship the same person with the same person.");
            return true;
        }

        Bukkit.broadcastMessage(ChatColor.GREEN + "" + sender_p.getName() + " ships " + notPacks + " x " + definitelyNotLemonHesForeverAlone + "." + ChatColor.LIGHT_PURPLE + " <3");
        return true;
    }
}
