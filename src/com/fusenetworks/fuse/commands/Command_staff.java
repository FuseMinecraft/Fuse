/*

Remove later

package com.fusenetworks.fuse.commands;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;

@CommandPermissions(source = SourceType.BOTH)
@CommandParameters(description = "Lists all the staff on Fuse Networks", usage = "/<command>",
aliases = "admins,adminlist,moderators,modlist,builders,builderlist,developers,devlist")
public class Command_staff extends BaseCommand {
    @Override
    public boolean run(final CommandSender sender, final Player sender_p, final Command cmd, final String commandLabel, final String[] args, final boolean senderIsConsole) 
    {
        String moderators = plugin.getConfig().getString("staff.moderator");
        String admins = plugin.getConfig().getString("staff.admin");
        String developers = plugin.getConfig().getString("staff.developer");
        String builders = plugin.getConfig().getString("staff.builder");
        String owner = plugin.getConfig().getString("staff.owner");
        sender.sendMessage(ChatColor.AQUA + "Fuse Networks Staff:");
        sender.sendMessage(ChatColor.AQUA + "Owner: " + owner);
        sender.sendMessage(ChatColor.AQUA + "Moderators: " + StringUtils.join(moderators) + "\n");
        sender.sendMessage(ChatColor.AQUA + "Admins: " + StringUtils.join(admins) + "\n");
        sender.sendMessage(ChatColor.AQUA + "Developers: " + StringUtils.join(developers) + "\n");
        sender.sendMessage(ChatColor.AQUA + "Builders: " + StringUtils.join(builders) + "\n");
        return true;
    }
}
*/