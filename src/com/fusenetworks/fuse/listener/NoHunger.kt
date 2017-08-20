package com.fusenetworks.fuse.listener

import com.fusenetworks.fuse.Fuse.Companion.plugin
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.entity.FoodLevelChangeEvent

class NoHunger : Listener {
    internal var hungerenabled = Companion.getPlugin().getConfig().getString("server.hunger_enabled")
    @EventHandler(priority = EventPriority.MONITOR)
    fun onFoodLevelChange(event: FoodLevelChangeEvent) {
        if (!hungerenabled.equals("true", ignoreCase = true)) {
            event.foodLevel = 20
        }
    }
}