package us.flowdesigns.commands;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

// Credit to TF

@CommandPermissions(source = SourceType.ONLY_IN_GAME)
@CommandParameters(description = "Makes a player execute a command", usage = "/<command>")
public class Command_gcmd extends BaseCommand
{
    @Override
    public boolean run(final CommandSender sender, final Player sender_p, final Command cmd, final String commandLabel, final String[] args, final boolean senderIsConsole)
    {
        if (!sender.hasPermission("nitrogen.gcmd"))
        {
            sender.sendMessage(Messages.MSG_NO_PERMS);
            return true;
        }

        if (args.length < 2)
        {
            sender.sendMessage("/gcmd <player> <command>");
            return true;
        }

        final Player player = getPlayer(args[0]);

        if (player == null)
        {
            sender.sendMessage(Messages.PLAYER_NOT_FOUND);
            return true;
        }

        final String outCommand = StringUtils.join(args, " ", 1, args.length);

        try
        {
            sender.sendMessage(ChatColor.GRAY + "Sending command as " + player.getName() + ": " + outCommand);
            if (server.dispatchCommand(player, outCommand))
            {
                sender.sendMessage(ChatColor.GRAY + "Command sent.");
            }
            else
            {
                sender.sendMessage(ChatColor.GRAY + "Unknown error sending command.");
            }
        }
        catch (Throwable ex)
        {
            sender.sendMessage(ChatColor.GRAY + "Error sending command: " + ex.getMessage());
        }

        return true;
    }
}
