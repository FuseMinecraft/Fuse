package com.fusenetworks.fuse.listener

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.block.BlockFace
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerMoveEvent
import org.bukkit.util.Vector

object Launchpads : Listener {
    internal var enabled =  Bukkit.getPluginManager().getPlugin("Fuse").getConfig().getString("launchpads.enabled")
    @EventHandler
    fun onPlayerMove(event: PlayerMoveEvent) {
        if (enabled.equals("true", ignoreCase = true)) {
            if (event.to.block.getRelative(BlockFace.DOWN).type == Material.REDSTONE_BLOCK) {
                val player = event.player
                if (player is Player) {
                    player.velocity = player.location.direction.multiply(1)
                    player.velocity = Vector(player.velocity.x, 0.5, player.velocity.z)
                }
            }
        } else {
            return
        }
    }
}