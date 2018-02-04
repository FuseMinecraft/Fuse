package com.fusenetworks.fuse.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(source = SourceType.ONLY_IN_GAME)
@CommandParameters(description = "Tells a person how to apply for admin", usage = "/<command>", aliases = "ai, apply")
public class Command_admininfo extends BaseCommand {
    @Override
    public boolean run(final CommandSender sender, final Player sender_p, final Command cmd, final String commandLabel, final String[] args, final boolean senderIsConsole) 
    {
        String applications_enabled = plugin.getConfig().getString("server.applications_enabled");
        String forums = plugin.getConfig().getString("server.forums");
        String admin_app_template = plugin.getConfig().getString("server.admin_application_template");
        String new_thread_link = plugin.getConfig().getString("server.admin_app_new_thread_link");
        if (senderIsConsole)
        {
            sender.sendMessage(Messages.PLAYER_ONLY);
            return true;
        }
        if (applications_enabled.equalsIgnoreCase("true")
                && !forums.equalsIgnoreCase("none")
                && !admin_app_template.equalsIgnoreCase("none")
                && !new_thread_link.equalsIgnoreCase("none"))
        {
        sender.sendMessage(ChatColor.RED + "To apply for moderator, register an account at: " + forums + "\n" +
        "When you've registered, copy and paste: " + admin_app_template + "\n" +
        "Create a new thread by clicking here: " + new_thread_link + "\n" +
        "Copy and paste the template into the thread\n" +
        "Do not bug staff members to look at your application or else it will most likely get denied");
        return true;
        } else {
            sender.sendMessage(ChatColor.RED + "Currently you can not apply for moderator, sorry.");
        }
        return true;
    }
}
