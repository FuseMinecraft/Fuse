package com.fusenetworks.fuse.listener

import com.fusenetworks.fuse.Fuse
import com.fusenetworks.fuse.Fuse.Companion.plugin
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.Entity
import org.bukkit.event.Listener
import org.bukkit.event.EventHandler
import org.bukkit.entity.Player
import org.bukkit.event.entity.PotionSplashEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.scheduler.BukkitRunnable

class PotionListener : Listener {

    internal var potions_enabled = Companion.getPlugin().getConfig().getString("server.splash_potions_enabled")
    @EventHandler
    fun onPotionSplashEvent(event: PotionSplashEvent) {
        val player = event.entity.shooter as Player
        val entity = event.entity

        when (event.potion.type) {
            EntityType.SPLASH_POTION -> {
                player.inventory.setItem(player.inventory.heldItemSlot, ItemStack(Material.AIR, 1))
                player.sendMessage(ChatColor.GOLD.toString() + "Fuse >> Splash potions are disabled")
                event.isCancelled = true
                object : BukkitRunnable() {
                    override fun run() {
                        event.affectedEntities.forEach { entity ->
                            event.potion.effects.stream().filter { effect -> entity is Player }.forEachOrdered { effect ->
                                val player = entity as Player
                                player.removePotionEffect(effect.type)
                            }
                        }
                    }
                }.runTaskLater(Fuse.getInstance(), 1)
            }
        }
    }
}