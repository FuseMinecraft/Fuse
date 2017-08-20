package com.fusenetworks.fuse.listener

import com.fusenetworks.fuse.Fuse.Companion.plugin

import com.fusenetworks.fuse.util.NUtil
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.plugin.java.JavaPlugin

import java.io.*
import java.lang.reflect.Method
import java.net.URL
import java.net.URLConnection
import java.util.logging.Level
import java.util.regex.Matcher
import java.util.regex.Pattern

class LoginMessages : Listener {
    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent): Boolean {
        val owner = Companion.getPlugin().getConfig().getString("staff.owner")
        val player = event.player
        if (player.name == owner) {
            Bukkit.broadcastMessage("§b$owner is the §4Owner§b!")
            return true
        }
        if (player.name == "OxLemonxO") {
            Bukkit.broadcastMessage("§bOxLemonxO is a §5Developer§b and the §4Ki§5ng §6Of §5Le§6mo§7ns§b!")
            return true
        }
        if (player.name == "xShotzfired_") {
            Bukkit.broadcastMessage("§bxShotzfired_ is an §9Admin §band §7The Glock King§b!")
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