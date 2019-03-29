package me.telesphoreo.commands;

import me.telesphoreo.utils.NUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(source = SourceType.BOTH)
@CommandParameters(description = "Removes server entities", usage = "/<command> [-s]", aliases = "entitywipe,ew,rd")
public class Command_clearlag extends BaseCommand
{
    @Override
    public boolean run(final CommandSender sender, final Player sender_p, final Command cmd, final String commandLabel, final String[] args, final boolean senderIsConsole)
    {
        if (!sender.hasPermission("nitrogen.clearlag"))
        {
            sender.sendMessage(Messages.MSG_NO_PERMS);
            return true;
        }

        if (args.length == 0)
        {
            NUtil.adminAction(sender.getName(), "Removing all server entities", false);
            if ((NUtil.NEntityWiper.wipeEntities(true, true)) == 1)
            {
                NUtil.playerMsg(sender, (NUtil.NEntityWiper.wipeEntities(true, true)) + " entity removed", ChatColor.GRAY);
                return true;
            }
            else
            {
                NUtil.playerMsg(sender, (NUtil.NEntityWiper.wipeEntities(true, true)) + " entities removed", ChatColor.GRAY);
            }
            return true;
        }
        else if (args[0].equalsIgnoreCase("-s"))
        {
            if ((NUtil.NEntityWiper.wipeEntities(true, true)) == 1)
            {
                NUtil.playerMsg(sender, (NUtil.NEntityWiper.wipeEntities(true, true)) + " entity removed", ChatColor.GRAY);
                return true;
            }
            else
            {
                NUtil.playerMsg(sender, (NUtil.NEntityWiper.wipeEntities(true, true)) + " entities removed", ChatColor.GRAY);
            }
        }
        else
        {
            return false;
        }
        return true;
    }
}