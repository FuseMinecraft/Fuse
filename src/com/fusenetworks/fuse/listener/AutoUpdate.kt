package com.fusenetworks.fuse.listener

import com.fusenetworks.fuse.Fuse
import org.bukkit.Bukkit
import org.bukkit.Bukkit.getServer
import org.bukkit.ChatColor
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.URL
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
            val updater = com.fusenetworks.fuse.Updater(Fuse())
            val oldVersion = updater.getVersionFromString(Bukkit.getPluginManager().getPlugin("Fuse").getDescription().getVersion())
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
                Bukkit.getPluginManager().getPlugin("Fuse").getLogger().log(Level.SEVERE, "Failed to automatically check for updates", e)
            }

        }
        return true
    }
}