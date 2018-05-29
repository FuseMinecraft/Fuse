package us.flowdesigns.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import us.flowdesigns.utils.History;

@CommandPermissions(source = SourceType.BOTH)
@CommandParameters(description = "Shows the name history of a player", usage = "/<command> <player>", aliases = "nh")
public class Command_namehistory extends BaseCommand {
    @Override
    public boolean run(final CommandSender sender, final Player sender_p, final Command cmd, final String commandLabel, final String[] args, final boolean senderIsConsole)
    {
        if (!sender.hasPermission("fuse.namehistory"))
        {
            sender.sendMessage(Messages.MSG_NO_PERMS);
            return true;
        }
        if (args.length != 1)
        {
            return false;
        }
        History.reportHistory(sender, args[0]);
        return true;
    }
}
