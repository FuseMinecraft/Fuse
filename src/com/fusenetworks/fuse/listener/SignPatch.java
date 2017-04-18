package com.fusenetworks.fuse.listener;
 
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class SignPatch implements Listener {

@EventHandler(priority=EventPriority.HIGH)
    public void onPlayerInteract(PlayerInteractEvent e) {
        if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            switch (e.getClickedBlock().getType()) {
                case SIGN: 
                case WALL_SIGN: 
                case SIGN_POST: {
                    e.setCancelled(true);
                }
            }
        }
    }
}