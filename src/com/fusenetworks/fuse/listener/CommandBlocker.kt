package com.fusenetworks.fuse.listener

import com.fusenetworks.fuse.Fuse.Companion.server
import org.bukkit.plugin.PluginManager
import org.bukkit.ChatColor
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerCommandPreprocessEvent
import org.bukkit.event.EventHandler

class CommandBlocker : Listener {
    internal val pm = Companion.getServer().getPluginManager()
    @EventHandler
    fun PlayerCommandPreprocessEvent(event: PlayerCommandPreprocessEvent): Boolean {
        val command = event.message
        val commandParts = command.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        if (commandParts[0].contains(":")) {
            event.player.sendMessage(ChatColor.GRAY.toString() + "Plugin specific commands are disabled")
            event.isCancelled = true
            return true
        }
        return true
    }
}