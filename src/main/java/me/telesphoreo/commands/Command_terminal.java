package me.telesphoreo.commands;

import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import static me.telesphoreo.utils.Terminal.runShellCommandAsync;

@CommandPermissions(source = SourceType.ONLY_IN_GAME)
@CommandParameters(description = "Send commands from the terminal", usage = "/<command>")
public class Command_terminal extends BaseCommand
{
    @Override
    public boolean run(final CommandSender sender, final Player sender_p, final Command cmd, final String commandLabel, final String[] args, final boolean senderIsConsole)
    {
        List superusers = plugin.getConfig().getList("players.superusers");

        if (!superusers.contains(sender.getName()))
        {
            sender.sendMessage(Messages.RESTRICTED_TO_SUPERUSERS);
            return true;
        }
        if (superusers.contains(sender.getName()))
        {
            if (args.length != 0)
            {
                runShellCommandAsync(sender, StringUtils.join(args, " "));
                return true;
            }
            sender.sendMessage("/terminal <command>");
            return true;
        }
        return true;
    }
}