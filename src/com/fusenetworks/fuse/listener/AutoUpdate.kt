package com.fusenetworks.fuse.listener

import com.fusenetworks.fuse.Fuse.Companion.plugin
import org.bukkit.Bukkit.getServer

import com.fusenetworks.fuse.Fuse
import com.oracle.deploy.update.Updater
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.PluginDescriptionFile
import org.bukkit.plugin.PluginManager

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.URL
import java.net.URLConnection
import java.util.logging.Level

class AutoUpdate : Listener {
    internal val versionLink = "https://vps76574.vps.ovh.ca/version.txt"
    internal var pm = getServer().pluginManager
    internal var p = pm.getPlugin("Fuse")
    internal var pdf = p.description
    internal var version = pdf.version

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent): Boolean {
        if (event.player.hasPermission("fuse.update") || event.player.isOp) {
            val updater = com.fusenetworks.fuse.Updater(Fuse.plugin)
            val oldVersion = updater.getVersionFromString(Companion.getPlugin().getDescription().getVersion())
            val path = updater.filePath

            try {
                val url = URL(versionLink)
                val con = url.openConnection()
                val isr = InputStreamReader(con.getInputStream())
                val reader = BufferedReader(isr)
                reader.ready()
                val newVersion = updater.getVersionFromString(reader.readLine())
                reader.close()

                if (newVersion > oldVersion) {

                    event.player.sendMessage(ChatColor.RED.toString() + "There is an update available for Fuse (" + pdf.version + " -> " + newVersion + "). To update Fuse, type /fuse update")
                }
            } catch (e: IOException) {
                Companion.getPlugin().getLogger().log(Level.SEVERE, "Failed to automatically check for updates", e)
            }

        }
        return true
    }
}