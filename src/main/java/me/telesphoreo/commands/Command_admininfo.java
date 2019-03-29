package me.telesphoreo.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(source = SourceType.ONLY_IN_GAME)
@CommandParameters(description = "Tells a person how to apply for admin", usage = "/<command>", aliases = "ai, apply")
public class Command_admininfo extends BaseCommand
{
    @Override
    public boolean run(final CommandSender sender, final Player sender_p, final Command cmd, final String commandLabel, final String[] args, final boolean senderIsConsole)
    {
        boolean applications_enabled = plugin.getConfig().getBoolean("commands.applications_enabled");
        String forums = plugin.getConfig().getString("commands.forums");
        String admin_app_template = plugin.getConfig().getString("commands.admin_application_template");
        String new_thread_link = plugin.getConfig().getString("commands.admin_app_new_thread_link");

        if (senderIsConsole)
        {
            sender.sendMessage(Messages.PLAYER_ONLY);
            return true;
        }

        if (applications_enabled
                && !forums.equalsIgnoreCase("none")
                && !admin_app_template.equalsIgnoreCase("none")
                && !new_thread_link.equalsIgnoreCase("none"))
        {
            sender.sendMessage(ChatColor.GOLD + "To apply for moderator, register an account at: " + forums + "\n" +
                    "When you've registered, copy the template: " + admin_app_template + "\n" +
                    "Create a new thread by clicking here: " + new_thread_link + "\n" +
                    "Paste the template into the thread and fill out the questions. Be sure you meet the requirements.\n" + ChatColor.RED +
                    "Do not bug staff members to look at your application or else it will most likely get denied");
            return true;
        }
        else
        {
            sender.sendMessage(Messages.UNKNOWN_COMMAND);
        }
        return true;
    }
}
