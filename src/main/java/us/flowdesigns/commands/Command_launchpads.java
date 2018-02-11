package us.flowdesigns.commands;

import us.flowdesigns.utils.NUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(source = SourceType.BOTH)
@CommandParameters(description = "Enable or disable launchpads", usage = "/<command> [on | off | status]")
public class Command_launchpads extends BaseCommand {
    @Override
    public boolean run(final CommandSender sender, final Player sender_p, final Command cmd, final String commandLabel, final String[] args, final boolean senderIsConsole)
    {
        String enabled = plugin.getConfig().getString("launchpads.enabled");
        
        if (!sender.hasPermission("fuse.launchpads"))
        {
            sender.sendMessage(Messages.MSG_NO_PERMS);
            return true;
        }

        if (args.length != 1)
        {
            return false;
        }
        
        if (args[0].equalsIgnoreCase("enable") || args[0].equalsIgnoreCase("on"))
        {
            NUtil.adminAction(sender.getName(), "Enabling launchpads", false);
            plugin.getConfig().set("launchpads.enabled", "true");
            plugin.saveConfig();
            plugin.reloadConfig();
            return true;
        }
        if (args[0].equalsIgnoreCase("disable") || args[0].equalsIgnoreCase("off"))
        {
            NUtil.adminAction(sender.getName(), "Disabling launchpads", false);
            plugin.getConfig().set("launchpads.enabled", "false");
            plugin.saveConfig();
            plugin.reloadConfig();
            return true;
        }
        if (args[0].equalsIgnoreCase("status"))
        {
            sender.sendMessage(ChatColor.BLUE + "Launchpads enabled: " + enabled);
            return true;
        }
        return true;
    }
}
