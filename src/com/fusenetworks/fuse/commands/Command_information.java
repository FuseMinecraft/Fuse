/*

Delete later

package com.fusenetworks.fuse.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;

@CommandPermissions(source = SourceType.BOTH)
@CommandParameters(description = "Gives basic information about which server a player is on", usage = "/<command>")
public class Command_information extends BaseCommand {
    @Override
    public boolean run(final CommandSender sender, final Player sender_p, final Command cmd, final String commandLabel, final String[] args, final boolean senderIsConsole) 
    {
        String servername = plugin.getConfig().getString("server.location");
        sender.sendMessage(ChatColor.RED + "Forums: www.play.packsnetwork.tk/forums\n" +
        "Website: http://www.play.packsnetwork.tk\n" +
        "Rules: /rules\n" +
        "Type /glist to see who is globally online\n" +
        "Type /server to list the servers and type /server name to join another server\n" +
        "To apply for admin type /ai\n" +
        "Type /contributors to see who contributed to Fuse Networks!");
        if (servername.equals("creative"))
        {
            sender.sendMessage(ChatColor.GREEN + "Type /p to get started with PlotMe\n" +
            "Type /p auto to get a new plot");
            return true;
        }
        if (servername.equals("factions"))
        {
            sender.sendMessage(ChatColor.GREEN + "Type /f to get started with Factions\n" +
            "Type /f create <name> to create a new faction\n" +
            "Type /warp wilderness to go to the wild\n" +
            "Type /warp shop to go to the shop");
            return true;
        }
        if (servername.equals("kitpvp"))
        {
            sender.sendMessage(ChatColor.GREEN + "Type /kits to list all of the kits\n" +
            "Type /kit <kitname> to get a kit");
            return true;
        }
        return true;
    }
}
*/