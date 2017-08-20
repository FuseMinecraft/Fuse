package com.fusenetworks.fuse.util

import com.fusenetworks.fuse.Fuse.plugin
import java.lang.reflect.Field
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.ArrayList
import java.util.Date
import java.util.Locale
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.World
import org.bukkit.command.CommandSender
import org.bukkit.entity.*

class NUtil {
    internal var location = plugin.config.getString("server.location")

    class NEntityWiper private constructor() {
        init {
            throw AssertionError()
        }

        companion object {
            private val WIPEABLES = ArrayList<Class<out Entity>>()

            init {
                WIPEABLES.add(EnderCrystal::class.java)
                WIPEABLES.add(EnderSignal::class.java)
                WIPEABLES.add(ExperienceOrb::class.java)
                WIPEABLES.add(Projectile::class.java)
                WIPEABLES.add(FallingBlock::class.java)
                WIPEABLES.add(Firework::class.java)
                WIPEABLES.add(Item::class.java)
            }

            private fun canWipe(entity: Entity, wipeExplosives: Boolean, wipeVehicles: Boolean): Boolean {
                if (wipeExplosives) {
                    if (Explosive::class.java!!.isAssignableFrom(entity.javaClass)) {
                        return true
                    }
                }
                if (wipeVehicles) {
                    if (Boat::class.java!!.isAssignableFrom(entity.javaClass)) {
                        return true
                    } else if (Minecart::class.java!!.isAssignableFrom(entity.javaClass)) {
                        return true
                    }
                }
                val it = WIPEABLES.iterator()
                while (it.hasNext()) {
                    if (it.next().isAssignableFrom(entity.javaClass)) {
                        return true
                    }
                }
                return false
            }

            fun wipeEntities(wipeExplosives: Boolean, wipeVehicles: Boolean): Int {
                var removed = 0
                val worlds = Bukkit.getWorlds().iterator()
                while (worlds.hasNext()) {
                    val entities = worlds.next().entities.iterator()
                    while (entities.hasNext()) {
                        val entity = entities.next()
                        if (canWipe(entity, wipeExplosives, wipeVehicles)) {
                            entity.remove()
                            removed++
                        }
                    }
                }
                return removed
            }
        }
    }

    companion object {
        var DATE_STORAGE_FORMAT = "EEE, MMM d, yyyy HH:mm:ss"
        fun bcastMsg(message: String, color: ChatColor?) {
            NLog.info(message, true)
            Bukkit.getOnlinePlayers().stream().forEach { player -> player.sendMessage((color ?: "").toString() + message) }
        }

        fun bcastMsg(message: String) {
            NUtil.bcastMsg(message, null)
        }

        fun adminAction(adminName: String, action: String, isRed: Boolean) {
            NUtil.bcastMsg(adminName + " - " + action, if (isRed) ChatColor.RED else ChatColor.BLUE)
        }

        fun playerMsg(sender: CommandSender, message: String, color: ChatColor) {
            sender.sendMessage(color.toString() + message)
        }

        fun playerMsg(sender: CommandSender, message: String) {
            NUtil.playerMsg(sender, message, ChatColor.GRAY)
        }

        fun moderatorAdminChat(name: String, message: String) {
            Bukkit.getOnlinePlayers().stream().filter { player -> player.hasPermission("fuse.adminchat") }.forEach { player -> player.sendMessage("§7[AdminChat] " + ChatColor.BLUE + name + " §8[§dModerator§8]" + "§f: " + ChatColor.AQUA + message) }
        }

        fun adminAdminChat(name: String, message: String) {
            Bukkit.getOnlinePlayers().stream().filter { player -> player.hasPermission("fuse.adminchat") }.forEach { player -> player.sendMessage("§7[AdminChat] " + ChatColor.BLUE + name + " §8[§9Admin§8]" + "§f: " + ChatColor.AQUA + message) }
        }

        fun devAdminChat(name: String, message: String) {
            Bukkit.getOnlinePlayers().stream().filter { player -> player.hasPermission("fuse.adminchat") }.forEach { player -> player.sendMessage("§7[AdminChat] " + ChatColor.BLUE + name + " §8[§5Dev§8]" + "§f: " + ChatColor.AQUA + message) }
        }

        fun builderAdminChat(name: String, message: String) {
            Bukkit.getOnlinePlayers().stream().filter { player -> player.hasPermission("fuse.adminchat") }.forEach { player -> player.sendMessage("§7[AdminChat] " + ChatColor.BLUE + name + " §8[§bBuilder§8]" + "§f: " + ChatColor.AQUA + message) }
        }

        fun nullAdminChat(name: String, message: String) {
            Bukkit.getOnlinePlayers().stream().filter { player -> player.hasPermission("fuse.adminchat") }.forEach { player -> player.sendMessage("§7[AdminChat] " + ChatColor.BLUE + name + "§f: " + ChatColor.AQUA + message) }
        }

        fun ownerAdminChat(name: String, message: String) {
            Bukkit.getOnlinePlayers().stream().filter { player -> player.hasPermission("fuse.adminchat") }.forEach { player -> player.sendMessage("§7[AdminChat] " + ChatColor.BLUE + name + " §8[§4Owner§8]" + "§f: " + ChatColor.AQUA + message) }
        }

        fun consoleAdminChat(name: String, message: String) {
            Bukkit.getOnlinePlayers().stream().filter { player -> player.hasPermission("fuse.adminchat") }.forEach { player -> player.sendMessage("§7[AdminChat] " + ChatColor.BLUE + name + " §8[§5Console§8]" + "§f: " + ChatColor.AQUA + message) }
        }

        fun dateToString(date: Date): String {
            return SimpleDateFormat(DATE_STORAGE_FORMAT, Locale.ENGLISH).format(date)
        }

        fun stringToDate(dateString: String): Date {
            try {
                return SimpleDateFormat(DATE_STORAGE_FORMAT, Locale.ENGLISH).parse(dateString)
            } catch (pex: ParseException) {
                return Date(0L)
            }

        }

        val nmsVersion: String
            get() {
                val packageName = Bukkit.getServer().javaClass.getPackage().getName()
                return packageName.substring(packageName.lastIndexOf('.') + 1)
            }

        fun colorize(string: String): String {
            return ChatColor.translateAlternateColorCodes('&', string)
        }

        fun setFlying(player: Player, flying: Boolean) {
            if (player.allowFlight == true) {
                player.allowFlight = true
                player.isFlying = flying
            } else {
                player.allowFlight = true
                player.isFlying = flying
                player.allowFlight = false
            }
        }

        fun <T> getField(from: Any, name: String): T? {
            var checkClass: Class<*> = from.javaClass
            do {
                try {
                    val field = checkClass.getDeclaredField(name)
                    field.isAccessible = true
                    return field.get(from) as T
                } catch (ex: NoSuchFieldException) {
                } catch (ex: IllegalAccessException) {
                }

            } while (checkClass.superclass != Any::class.java && (checkClass = checkClass.superclass) != null)
            return null
        }
    }
}