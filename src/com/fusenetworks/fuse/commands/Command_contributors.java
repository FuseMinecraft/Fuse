package com.fusenetworks.fuse.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(source = SourceType.BOTH)
@CommandParameters(description = "Shows the people who contributed to Fuse", usage = "/<command>", aliases = "contributers")
public class Command_contributors extends BaseCommand {
    @Override
    public boolean run(final CommandSender sender, final Player sender_p, final Command cmd, final String commandLabel, final String[] args, final boolean senderIsConsole) 
    {
        sender.sendMessage(ChatColor.RED + "Contributors to Fuse:\n" +
                "Telesphoreo - Developer\n" +
                "OxLemonxO - Developer\n" +
                "Madgeek1450, Prozza - TotalFreedomMod code\n" +
                "TheMinecraft (_Windows) - Unload chunks command");
        return true;
    }
}
