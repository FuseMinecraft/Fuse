package com.fusenetworks.fuse.commands;

import static com.fusenetworks.fuse.util.Terminal.runShellCommandAsync;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(source = SourceType.ONLY_IN_GAME)
@CommandParameters(description = "Terminal command", usage = "/<command>")
public class Command_terminal extends BaseCommand {
    @Override
    public boolean run(final CommandSender sender, final Player sender_p, final Command cmd, final String commandLabel, final String[] args, final boolean senderIsConsole) 
    {
    String superuers = plugin.getConfig().getString("players.superusers");
    if (!superuers.contains(sender.getName()))
    {
        sender.sendMessage(Messages.MSG_NO_PERMS);
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