package com.fusenetworks.fuse.listener;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class ConfigListener implements Listener {

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        if (Bukkit.getPluginManager().getPlugin("Fuse").getConfig().getString("server.drop_items_on_death").equalsIgnoreCase("false"))
        {
            e.getDrops().clear();
        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent e) {
        if (e.getPlayer().hasPermission("fuse.nodrop")) {
            e.getPlayer().sendMessage(ChatColor.RED + "You cannot drop items!");
            e.setCancelled(true);
        } else {
            e.setCancelled(false);
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e)
    {
        Player player = e.getPlayer();
        String teleportSpawn = Bukkit.getPluginManager().getPlugin("Fuse").getConfig().getString("server.spawn_on_join");
        String clearInventoryOnJoin = Bukkit.getPluginManager().getPlugin("Fuse").getConfig().getString("server.clear_inventory_on_join");
        if ("true".equals(teleportSpawn)) {
            player.chat("/spawn");
            // Fix later - teleporting to the wrong world
        }
        if ("true".equals(clearInventoryOnJoin)) {
            player.getPlayer().getInventory().clear();
        }
    }
}