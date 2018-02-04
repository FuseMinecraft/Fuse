package com.fusenetworks.fuse.listener;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

import static com.fusenetworks.fuse.Fuse.plugin;

public class Launchpads implements Listener {
    String enabled = plugin.getConfig().getString("launchpads.enabled");
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (enabled.equalsIgnoreCase("true"))
        {
            if (event.getTo().getBlock().getRelative(BlockFace.DOWN).getType() == Material.REDSTONE_BLOCK) {
                Player player = event.getPlayer();
                if (player instanceof Player)
                {
                player.setVelocity(player.getLocation().getDirection().multiply(1));
                player.setVelocity(new Vector(player.getVelocity().getX(), 0.5D, player.getVelocity().getZ()));
               }
            }
        } else {
            return;
        }
    }
}