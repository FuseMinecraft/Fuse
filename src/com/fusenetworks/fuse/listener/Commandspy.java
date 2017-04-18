package com.fusenetworks.fuse.listener;

import com.fusenetworks.fuse.util.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.EventPriority;
import org.bukkit.event.EventHandler;

public class Commandspy implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
        Bukkit.getOnlinePlayers().stream().filter((pl) -> (PlayerData.getPlayerData(pl).cmdspyEnabled())).forEach((Player pl) -> {
            final Player player = event.getPlayer();
            String command = event.getMessage();
            if (pl != player) {
                pl.sendMessage(ChatColor.GRAY + player.getName() + ": " + command);
            }
        });
    }
}
