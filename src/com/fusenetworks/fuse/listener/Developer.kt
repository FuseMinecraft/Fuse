package com.fusenetworks.fuse.listener

import com.fusenetworks.fuse.Fuse.Companion.plugin

import com.fusenetworks.fuse.Fuse
import com.oracle.deploy.update.Updater
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class Developer : Listener {
    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent): Boolean {
        val dev = Companion.getPlugin().getConfig().getString("server.dev")
        val player = event.player
        if (dev == "true") {
            player.sendMessage(ChatColor.DARK_AQUA.toString() + "Warning: The server is currently in development mode. "
                    + "This means there may be unstable plugin builds on this server, and the server could crash more than normal!")
            return true
        }
        return true
    }

}