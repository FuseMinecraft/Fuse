package com.fusenetworks.fuse.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;

@CommandPermissions(source = SourceType.BOTH)
@CommandParameters(description = "Gives a link to the website", usage = "/<command>")
public class Command_website extends BaseCommand {
    @Override
    public boolean run(final CommandSender sender, final Player sender_p, final Command cmd, final String commandLabel, final String[] args, final boolean senderIsConsole) 
    {
        String website = plugin.getConfig().getString("server.website");
        if (!website.equalsIgnoreCase("none"))
        {
        sender.sendMessage(ChatColor.AQUA + "Website: " + website);
        } else {
            sender.sendMessage(ChatColor.RED + "There is not a website for this server");
        }
        return true;
    }
}
