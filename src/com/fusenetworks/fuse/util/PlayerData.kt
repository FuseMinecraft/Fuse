package com.fusenetworks.fuse.util


import java.util.HashMap
import java.util.UUID
import org.bukkit.Bukkit
import org.bukkit.entity.Player

class PlayerData(private val player: Player, private val uuid: UUID, private val ip: String) {
    private var cmdspyEnabled = false
    fun setCommandSpy(enabled: Boolean) {
        this.cmdspyEnabled = enabled
    }

    fun cmdspyEnabled(): Boolean {
        return cmdspyEnabled
    }

    companion object {
        val PLAYER_DATA: MutableMap<String, PlayerData> = HashMap()
        val AUTO_PURGE = 20L * 60L * 5L
        fun hasPlayerData(player: Player): Boolean {
            return PLAYER_DATA.containsKey(player.player.address.address.hostAddress.trim { it <= ' ' })
        }

        fun getPlayerDataSync(player: Player): PlayerData {
            synchronized(PLAYER_DATA) {
                return getPlayerData(player)
            }
        }

        fun getPlayerData(player: Player): PlayerData {
            val ip = player.player.address.address.hostAddress.trim { it <= ' ' }
            var data: PlayerData? = PlayerData.PLAYER_DATA[ip]
            if (data != null) {
                return data
            }
            if (Bukkit.getOnlineMode()) {
                for (dataTest in PLAYER_DATA.values) {
                    if (dataTest.player.name.equals(player.name, ignoreCase = true)) {
                        data = dataTest
                        break
                    }
                }
            }
            if (data != null) {
                return data
            }
            data = PlayerData(player, player.uniqueId, ip)
            PlayerData.PLAYER_DATA.put(ip, data)
            return data
        }
    }
}