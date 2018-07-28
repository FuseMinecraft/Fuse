package us.flowdesigns.commands;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(source = SourceType.ONLY_CONSOLE)
@CommandParameters(description = "Chat from the console", usage = "/<command>", aliases = "csay")
public class Command_consolesay extends BaseCommand
{
    @Override
    public boolean run(final CommandSender sender, final Player sender_p, final Command cmd, final String commandLabel, final String[] args, final boolean senderIsConsole)
    {
        if (args.length == 0)
        {
            if (senderIsConsole)
            {
                sender.sendMessage(Messages.NO_MSG);
                return true;
            }
            else
            {
                sender.sendMessage(Messages.CONSOLE_ONLY);
            }
            return true;
        }
        if (args.length > 0 && senderIsConsole)
        {
            Bukkit.broadcastMessage(String.format("§7[CONSOLE] §f<§c%s§f> %s", sender.getName(), StringUtils.join(args, " ")));
            return true;
        }
        else
        {
            sender.sendMessage(Messages.CONSOLE_ONLY);
        }
        return true;
    }
}
