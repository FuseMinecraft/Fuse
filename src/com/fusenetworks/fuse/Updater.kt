package com.fusenetworks.fuse

import com.fusenetworks.fuse.util.NUtil
import java.io.BufferedReader
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.reflect.Method
import java.net.URL
import java.net.URLConnection
import java.util.logging.Level
import java.util.regex.Matcher
import java.util.regex.Pattern
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.PluginDescriptionFile
import org.bukkit.plugin.PluginManager
import org.bukkit.plugin.java.JavaPlugin

import org.bukkit.Bukkit.getPlayer
import org.bukkit.Bukkit.getServer

class Updater(private val plugin: Plugin) {
    internal val dlLink = "https://vps76574.vps.ovh.ca/Fuse.jar"
    internal val versionLink = "https://vps76574.vps.ovh.ca/version.txt"

    fun update(sender: CommandSender) {
        val oldVersion = this.getVersionFromString(plugin.description.version)
        val path = this.filePath

        try {
            var url = URL(versionLink)
            var con = url.openConnection()
            val isr = InputStreamReader(con.getInputStream())
            val reader = BufferedReader(isr)
            reader.ready()
            val newVersion = this.getVersionFromString(reader.readLine())
            reader.close()

            if (newVersion > oldVersion) {
                url = URL(dlLink)
                con = url.openConnection()
                val `in` = con.getInputStream()
                val out = FileOutputStream(path)
                val buffer = ByteArray(1024)
                var size = 0
                while (`in`.read(buffer).let { size = it; it != -1 }) {
                    out.write(buffer, 0, size)
                }

                out.close()
                `in`.close()
                plugin.logger.log(Level.INFO, "Updating to the latest version of Fuse")
                NUtil.bcastMsg(sender.name + " - Updating to the latest version of Fuse", ChatColor.BLUE)
                NUtil.bcastMsg(ChatColor.BLUE.toString() + "Please wait.")
                Bukkit.reload()
                NUtil.bcastMsg(ChatColor.BLUE.toString() + "Update successful.")
            } else {
                sender.sendMessage(ChatColor.GRAY.toString() + "There are no updates available for Fuse.")
            }
        } catch (e: IOException) {
            plugin.logger.log(Level.SEVERE, "Failed to auto-update", e)
        }

    }

    val filePath: String
        get() = if (plugin is JavaPlugin) {
            try {
                val method = JavaPlugin::class.java!!.getDeclaredMethod("getFile")
                val wasAccessible = method.isAccessible()
                method.setAccessible(true)
                val file = method.invoke(plugin) as File
                method.setAccessible(wasAccessible)

                file.path
            } catch (e: Exception) {
                "plugins" + File.separator + plugin.getName()
            }

        } else {
            "plugins" + File.separator + plugin.name
        }

    fun getVersionFromString(from: String): Int {
        var result = ""
        val pattern = Pattern.compile("\\d+")
        val matcher = pattern.matcher(from)

        while (matcher.find()) {
            result += matcher.group()
        }

        return if (result.isEmpty()) 0 else Integer.parseInt(result)
    }
}