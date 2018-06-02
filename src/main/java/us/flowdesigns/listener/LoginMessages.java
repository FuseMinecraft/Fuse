package us.flowdesigns.listener;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;

import static us.flowdesigns.fuse.Fuse.plugin;

public class LoginMessages implements Listener {
    @EventHandler
    public boolean onPlayerJoin(PlayerJoinEvent event) {
        String owner = plugin.getConfig().getString("players.owner");
        String login_messages_enabled = plugin.getConfig().getString("server.login_messages_enabled");
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        // Telesphoreo
        if (uuid.toString().equals("78408086-1991-4c33-a571-d8fa325465b2") && !player.getName().equals(owner))
        {
            Bukkit.broadcastMessage("§b" + player.getName() + " is a Developer for Fuse");
            return true;
        }
        // OxLemonxO
        if (uuid.toString().equals("e628c2b0-0e19-41d9-bb9e-af604fcb159a")) {
            Bukkit.broadcastMessage("§b" + player.getName() + " is a Developer for Fuse and the §4Ki§5ng §6Of §5Le§6mo§7ns§b!");
            return true;
        }
        if (login_messages_enabled.equalsIgnoreCase("true"))
        {
            if (player.getName().equals(owner)) {
                Bukkit.broadcastMessage("§b" + owner + " is the §4Owner§b!");
                return true;
            }
            if (player.getPlayer().hasPermission("fuse.moderator")) {
                Bukkit.broadcastMessage(ChatColor.AQUA + player.getName() + " is a §dModerator§b!");
                return true;
            } else if (player.getPlayer().hasPermission("fuse.admin")) {
                Bukkit.broadcastMessage(ChatColor.AQUA + player.getName() + " is an §9Admin§b!");
                return true;
            } else if (player.getPlayer().hasPermission("fuse.developer")) {
                Bukkit.broadcastMessage(ChatColor.AQUA + player.getName() + " is a §5Developer§b!");
                return true;
            } else if (player.getPlayer().hasPermission("fuse.builder")) {
                Bukkit.broadcastMessage(ChatColor.AQUA + player.getName() + " is a §bBuilder!");
                return true;
            }
        }
        return true;
    }
}