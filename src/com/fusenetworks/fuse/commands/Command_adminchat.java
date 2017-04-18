package com.fusenetworks.fuse.commands;

import com.fusenetworks.fuse.util.NLog;
import com.fusenetworks.fuse.util.NUtil;
import org.apache.commons.lang.StringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(source = SourceType.BOTH)
@CommandParameters( description = "AdminChat - Talk privately with other admins", usage = "/<command> [message...]", aliases = "o")
public class Command_adminchat extends BaseCommand {

    @Override
    public boolean run(final CommandSender sender, final Player sender_p, final Command cmd, final String commandLabel, final String[] args, final boolean senderIsConsole) {

        if (args.length == 0) {
            sender.sendMessage(Messages.NO_MSG);
            return true;
        }

        if (sender.hasPermission("fuse.adminchat"))
        {
        if (sender instanceof Player)
        // needs cleanup
        {
            if (sender.hasPermission("fuse.moderator"))
            {
            NUtil.moderatorAdminChat(sender.getName(), StringUtils.join(args, " "));
            return true;
            }
            //
            if (sender.hasPermission("fuse.admin"))
            {
            NUtil.adminAdminChat(sender.getName(), StringUtils.join(args, " "));
            return true;
            }
            //
            if (sender.hasPermission("fuse.developer"))
            {
            NUtil.devAdminChat(sender.getName(), StringUtils.join(args, " "));
            return true;
            }
            //
            if (sender.hasPermission("fuse.builder"))
            {
            NUtil.builderAdminChat(sender.getName(), StringUtils.join(args, " "));
            return true;
            }
            //
            if (sender.hasPermission("fuse.owner"))
            {
            NUtil.ownerAdminChat(sender.getName(), StringUtils.join(args, " "));
            return true;
            }
            //
            NLog.info("[AdminChat] " + sender.getName() + ": " + StringUtils.join(args, " "));
            return true;
        } else {
            NUtil.consoleAdminChat(sender.getName(), StringUtils.join(args, " "));   
            NLog.info("[AdminChat] " + sender.getName() + " [Console]: " + StringUtils.join(args, " "));
        }
        } else {
            if (sender.isOp())
            {
            NUtil.nullAdminChat(sender.getName(), StringUtils.join(args, " "));
            } else {
                sender.sendMessage(Messages.MSG_NO_PERMS);
            }
            
        }
    return true;
    }
}
