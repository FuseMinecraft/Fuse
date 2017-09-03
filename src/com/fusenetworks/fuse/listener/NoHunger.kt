package com.fusenetworks.fuse.listener

import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.entity.FoodLevelChangeEvent

object NoHunger : Listener {
    internal var hungerenabled =  Bukkit.getPluginManager().getPlugin("Fuse").getConfig().getString("server.hunger_enabled")
    @EventHandler(priority = EventPriority.MONITOR)
    fun onFoodLevelChange(event: FoodLevelChangeEvent) {
        if (!hungerenabled.equals("true", ignoreCase = true)) {
            event.foodLevel = 20
        }
    }
}