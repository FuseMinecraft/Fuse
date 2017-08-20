package com.fusenetworks.fuse.listener

import com.fusenetworks.fuse.Fuse.Companion.plugin
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.entity.EntityDamageEvent.DamageCause

class NoFall : Listener {
    internal var falldamage = Companion.getPlugin().getConfig().getString("server.fall_damage_enabled")
    @EventHandler(priority = EventPriority.MONITOR)
    fun onPlayerFall(event: EntityDamageEvent) {
        if (!falldamage.equals("true", ignoreCase = true)) {
            if (event.cause == DamageCause.FALL) {
                event.isCancelled = true
            }
        }
    }
}