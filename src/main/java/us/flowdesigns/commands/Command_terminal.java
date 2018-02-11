package us.flowdesigns.commands;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static us.flowdesigns.utils.Terminal.runShellCommandAsync;

@CommandPermissions(source = SourceType.ONLY_IN_GAME)
@CommandParameters(description = "Send commands from the terminal", usage = "/<command>")
public class Command_terminal extends BaseCommand {
    @Override
    public boolean run(final CommandSender sender, final Player sender_p, final Command cmd, final String commandLabel, final String[] args, final boolean senderIsConsole)
    {
    String superusers = plugin.getConfig().getString("players.superusers");
    if (!superusers.equals(sender.getName()))
    {
        sender.sendMessage(Messages.MSG_NO_PERMS);
        return true;
    }
    if (args.length == 0)
    {
        sender.sendMessage("/terminal <command>");
        return true;
    }
    runShellCommandAsync(sender, StringUtils.join(args, " "));
    return true;
    }

}