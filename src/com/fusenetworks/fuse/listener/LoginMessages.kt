package com.fusenetworks.fuse.listener

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class LoginMessages : Listener {
    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent): Boolean {
        val owner =  Bukkit.getPluginManager().getPlugin("Fuse").getConfig().getString("server.owner")
        val player = event.player
        if (player.name == owner) {
            Bukkit.broadcastMessage("§b$owner is the §4Owner§b!")
            return true
        }
        if (player.name == "OxLemonxO") {
            Bukkit.broadcastMessage("§bOxLemonxO is a §5Developer§b and the §4Ki§5ng §6Of §5Le§6mo§7ns§b!")
            return true
        }
        if (player.player.hasPermission("fuse.moderator")) {
            Bukkit.broadcastMessage(ChatColor.AQUA.toString() + player.name + " is a §dModerator§b!")
            return true
        } else if (player.player.hasPermission("fuse.admin")) {
            Bukkit.broadcastMessage(ChatColor.AQUA.toString() + player.name + " is an §9Admin§b!")
            return true
        } else if (player.player.hasPermission("fuse.developer")) {
            Bukkit.broadcastMessage(ChatColor.AQUA.toString() + player.name + " is a §5Developer§b!")
            return true
        } else if (player.player.hasPermission("fuse.builder")) {
            Bukkit.broadcastMessage(ChatColor.AQUA.toString() + player.name + " is a §bBuilder!")
            return true
        }
        return true
    }
}