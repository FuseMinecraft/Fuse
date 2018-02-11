package us.flowdesigns.utils;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import us.flowdesigns.fuse.Fuse;

public class FSync
{

    public static void playerMsg(final Player player, final String message)
    {
        final Fuse plugin = Fuse.plugin;
        new BukkitRunnable()
        {

            @Override
            public void run()
            {
                NUtil.playerMsg(player, message);
            }

        }.runTask(plugin);
    }
    public static void playerMsg(final CommandSender sender, final String message)
    {
        final Fuse plugin = Fuse.plugin;
        new BukkitRunnable()
        {

            @Override
            public void run()
            {
                sender.sendMessage(message);
            }

        }.runTask(plugin);
    }
}