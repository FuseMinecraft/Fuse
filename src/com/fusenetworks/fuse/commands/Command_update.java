package com.fusenetworks.fuse.commands;

import com.fusenetworks.fuse.Fuse;
import com.fusenetworks.fuse.Updater;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(source = SourceType.BOTH)
@CommandParameters(description = "Updates Fuse", usage = "/<command>")
public class Command_update extends BaseCommand {
    @Override
    public boolean run(final CommandSender sender, final Player sender_p, final Command cmd, final String commandLabel, final String[] args, final boolean senderIsConsole) 
    {
        if (!sender.hasPermission("fuse.update"))
        {
            sender.sendMessage(Messages.MSG_NO_PERMS);
            return true;
        }
        Updater updater = new Updater(Fuse.plugin);
        updater.update(sender);
        return true;
    }
}
