package us.flowdesigns.listener;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.PluginManager;

import static us.flowdesigns.fuse.Fuse.server;

public class CommandBlocker implements Listener {
    final PluginManager pm = server.getPluginManager();
    @EventHandler
    public boolean PlayerCommandPreprocessEvent(PlayerCommandPreprocessEvent event) {
    String command = event.getMessage();
    final String[] commandParts = command.split(" ");
        if (commandParts[0].contains(":"))
        {
            event.getPlayer().sendMessage(ChatColor.GRAY + "Plugin specific commands are disabled");
            event.setCancelled(true);
            return true;
        }
    return true;
    }
}