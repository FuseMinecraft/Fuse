package com.fusenetworks.fuse.util;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PlayerData {
    public static final Map<String, PlayerData> PLAYER_DATA = new HashMap<>();
    public static final long AUTO_PURGE = 20L * 60L * 5L;
    public static boolean hasPlayerData(Player player) {
        return PLAYER_DATA.containsKey(player.getPlayer().getAddress().getAddress().getHostAddress().trim());
    }
    public static PlayerData getPlayerDataSync(Player player) {
        synchronized (PLAYER_DATA) {
            return getPlayerData(player);
        }
    }
    public static PlayerData getPlayerData(Player player) {
        final String ip = player.getPlayer().getAddress().getAddress().getHostAddress().trim();
        PlayerData data = PlayerData.PLAYER_DATA.get(ip);
        if (data != null) {
            return data;
        }
        if (Bukkit.getOnlineMode()) {
            for (PlayerData dataTest : PLAYER_DATA.values()) {
                if (dataTest.player.getName().equalsIgnoreCase(player.getName())) {
                    data = dataTest;
                    break;
                }
            }
        }
        if (data != null) {
            return data;
        }
        data = new PlayerData(player, player.getUniqueId(), ip);
        PlayerData.PLAYER_DATA.put(ip, data);
        return data;
    }
    private final Player player;
    private final String ip;
    private final UUID uuid;
    private boolean cmdspyEnabled = false;
    public PlayerData(Player player, UUID uuid, String ip) {
        this.player = player;
        this.uuid = uuid;
        this.ip = ip;
    }
    public void setCommandSpy(boolean enabled) {
        this.cmdspyEnabled = enabled;
    }
    public boolean cmdspyEnabled() {
        return cmdspyEnabled;
    }
}