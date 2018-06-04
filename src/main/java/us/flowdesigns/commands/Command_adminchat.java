package us.flowdesigns.commands;

import org.apache.commons.lang.StringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import us.flowdesigns.utils.NLog;
import us.flowdesigns.utils.NUtil;

import static us.flowdesigns.fuse.Fuse.plugin;

@CommandPermissions(source = SourceType.BOTH)
@CommandParameters(description = "Talk privately with other admins", usage = "/<command> [message...]", aliases = "o")
public class Command_adminchat extends BaseCommand {

    @Override
    public boolean run(final CommandSender sender, final Player sender_p, final Command cmd, final String commandLabel, final String[] args, final boolean senderIsConsole) {
        if (!sender.hasPermission("fuse.adminchat"))
        {
            sender.sendMessage(Messages.MSG_NO_PERMS);
            return true;
        }
        if (!senderIsConsole)
        {
            if (args.length == 0)
            {
                sender.sendMessage(Messages.NO_MSG);
                return true;
            }
            NUtil.playerAdminChat(sender.getName(), StringUtils.join(args, " "));
            NLog.info("[AdminChat] " + sender.getName() + ": " + StringUtils.join(args, " "));
            return true;
        }
        if (senderIsConsole)
        {
            if (args.length == 0)
            {
                sender.sendMessage(Messages.NO_MSG);
                return true;
            }
            NUtil.consoleAdminChat(sender.getName(), StringUtils.join(args, " "));
            NLog.info("[AdminChat] " + sender.getName() + " [Console]: " + StringUtils.join(args, " "));
        }
        return true;
    }
}
