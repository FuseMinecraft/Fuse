package me.telesphoreo.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

@CommandPermissions(source = SourceType.BOTH)
@CommandParameters(description = "Shows all of the servers links", usage = "/<command>")
public class Command_links extends BaseCommand
{
    @Override
    public boolean run(final CommandSender sender, final Player sender_p, final Command cmd, final String commandLabel, final String[] args, final boolean senderIsConsole)
    {
        ConfigurationSection section = plugin.getConfig().getConfigurationSection("social_links");
        if (section != null)
        {
            Map<String, Object> values = section.getValues(false);

            List<String> lines = new ArrayList<>();

            for (String key : values.keySet())
            {
                if (!(values.get(key) instanceof String))
                {
                    continue;
                }
                String link = (String)values.get(key);
                lines.add(ChatColor.GOLD + "- " + key + ": " + ChatColor.AQUA + link);
            }

            sender.sendMessage(ChatColor.AQUA + "Social Media Links:");
            sender.sendMessage(lines.toArray(new String[0]));
            return true;
        }
        else
        {
            sender.sendMessage(ChatColor.RED + "There are no links added in the configuration file.");
        }
        return true;
    }
}
