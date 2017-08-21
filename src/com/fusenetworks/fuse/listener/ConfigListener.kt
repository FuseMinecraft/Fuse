package com.fusenetworks.fuse.listener

import com.fusenetworks.fuse.Fuse.Companion.plugin
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.player.PlayerDropItemEvent
import org.bukkit.event.player.PlayerJoinEvent

class ConfigListener : Listener {

    @EventHandler
    fun onPlayerDeath(e: PlayerDeathEvent) {
        if (Bukkit.getPluginManager().getPlugin("Fuse").getConfig().getString("server.drop_items_on_death").equals("false")) {
            e.drops.clear()
        }
    }

    @EventHandler
    fun onDrop(e: PlayerDropItemEvent) {
        if (e.player.hasPermission("fuse.nodrop")) {
            e.player.sendMessage(ChatColor.RED.toString() + "You cannot drop items!")
            e.isCancelled = true
        } else {
            e.isCancelled = false
        }
    }

    @EventHandler
    fun onPlayerJoin(e: PlayerJoinEvent) {
        val player = e.player
        val teleportSpawn = Bukkit.getPluginManager().getPlugin("Fuse").getConfig().getString("server.spawn_on_join")
        val clearInventoryOnJoin =  Bukkit.getPluginManager().getPlugin("Fuse").getConfig().getString("server.clear_inventory_on_join")
        if ("true" == teleportSpawn) {
            player.chat("/spawn")
            // Fix later - teleporting to the wrong world
        }
        if ("true" == clearInventoryOnJoin) {
            player.player.inventory.clear()
        }
    }
}
