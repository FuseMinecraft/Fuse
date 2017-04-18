package com.fusenetworks.fuse.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;

@CommandPermissions(source = SourceType.BOTH)
@CommandParameters(description = "Gives a link to the Discord server", usage = "/<command>")
public class Command_discord extends BaseCommand {
    @Override
    public boolean run(final CommandSender sender, final Player sender_p, final Command cmd, final String commandLabel, final String[] args, final boolean senderIsConsole) 
    {
        String discord = plugin.getConfig().getString("server.discord");
        if (!discord.equalsIgnoreCase("none"))
        {
        sender.sendMessage(ChatColor.AQUA + "Discord: " + discord);
        } else {
            sender.sendMessage(ChatColor.RED + "There is no Discord setup for this server");
        }
        return true;
    }
}
