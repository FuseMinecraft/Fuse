package com.fusenetworks.fuse.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import static com.fusenetworks.fuse.Fuse.plugin;

public class NoFall implements Listener
{
    String falldamage = plugin.getConfig().getString("server.fall_damage_enabled");
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerFall(EntityDamageEvent event)
    {
        if (!falldamage.equalsIgnoreCase("true"))
        {
            if (event.getCause() == DamageCause.FALL)
            {
                event.setCancelled(true);
            }
        }
    }
}