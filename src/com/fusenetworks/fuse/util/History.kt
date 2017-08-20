package com.fusenetworks.fuse.util

import com.fusenetworks.fuse.Fuse
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Arrays
import java.util.Date
import java.util.UUID
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.ChatColor
import org.bukkit.command.CommandSender

object History {

    val df: DateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

    fun reportHistory(sender: CommandSender, username: String) {
        object : BukkitRunnable() {
            override fun run() {
                val uuid = UUIDFetcher.fetch(username)
                if (uuid != null) {
                    val gson = GsonBuilder().create()
                    val compactUuid = uuid.toString().replace("-", "")
                    try {
                        val url = URL("https://api.mojang.com/user/profiles/$compactUuid/names")
                        val conn = url.openConnection() as HttpURLConnection
                        val reader = BufferedReader(InputStreamReader(conn.inputStream))
                        val oldNames = gson.fromJson<Array<FName>>(reader, Array<FName>::class.java!!)
                        if (oldNames == null) {
                            FSync.playerMsg(sender, ChatColor.RED.toString() + "Player not found!")
                            return
                        }
                        reader.close()
                        conn.disconnect()
                        Arrays.sort(oldNames)
                        printHistory(sender, oldNames)
                    } catch (ex: Exception) {
                        FSync.playerMsg(sender, ChatColor.RED.toString() + "Error, see logs for more details.")
                        NLog.severe(ex)
                    }

                } else {
                    FSync.playerMsg(sender, ChatColor.RED.toString() + "Player not found!")
                }
            }
        }.runTaskAsynchronously(Fuse.plugin)
    }

    private fun printHistory(sender: CommandSender, oldNames: Array<FName>?) {
        if (oldNames!!.size == 1) {
            FSync.playerMsg(sender, ChatColor.GREEN + oldNames[0].name + ChatColor.GOLD + " has never changed their name.")
            return
        }
        FSync.playerMsg(sender, ChatColor.GOLD.toString() + "Original name: " + ChatColor.GREEN + oldNames[0].name)
        for (i in 1..oldNames.size - 1) {
            val date = Date(oldNames[i].changedToAt)
            val formattedDate = df.format(date)
            FSync.playerMsg(sender, ChatColor.BLUE.toString() + formattedDate + ChatColor.GOLD + " changed to " + ChatColor.GREEN + oldNames[i].name)
        }
    }

    private class FName : Comparable<FName> {

        val name: String? = null
        val changedToAt: Long = 0

        override fun compareTo(other: FName): Int {
            return java.lang.Long.compare(this.changedToAt, other.changedToAt)
        }
    }
}