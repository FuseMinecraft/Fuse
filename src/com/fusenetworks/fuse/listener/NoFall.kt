package com.fusenetworks.fuse.listener

import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.entity.EntityDamageEvent.DamageCause

object NoFall : Listener {
    internal var falldamage =  Bukkit.getPluginManager().getPlugin("Fuse").getConfig().getString("server.fall_damage_enabled")
    @EventHandler(priority = EventPriority.MONITOR)
    fun onPlayerFall(event: EntityDamageEvent) {
        if (!falldamage.equals("true", ignoreCase = true)) {
            if (event.cause == DamageCause.FALL) {
                event.isCancelled = true
            }
        }
    }
}