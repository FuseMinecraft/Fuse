package com.fusenetworks.fuse.listener

import com.fusenetworks.fuse.util.PlayerData
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerCommandPreprocessEvent
import org.bukkit.event.EventPriority
import org.bukkit.event.EventHandler

class Commandspy : Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    fun onPlayerCommandPreprocess(event: PlayerCommandPreprocessEvent) {
        Bukkit.getOnlinePlayers().stream().filter { pl -> PlayerData.getPlayerData(pl).cmdspyEnabled() }.forEach { pl: Player ->
            val player = event.player
            val command = event.message
            if (pl !== player) {
                pl.sendMessage(ChatColor.GRAY.toString() + player.name + ": " + command)
            }
        }
    }
}
