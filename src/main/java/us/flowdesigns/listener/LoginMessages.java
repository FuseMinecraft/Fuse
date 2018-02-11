package us.flowdesigns.listener;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import static us.flowdesigns.fuse.Fuse.plugin;

public class LoginMessages implements Listener {
    @EventHandler
    public boolean onPlayerJoin(PlayerJoinEvent event) {
        String owner = plugin.getConfig().getString("server.owner");
        Player player = event.getPlayer();
        if (player.getName().equals(owner)) {
            Bukkit.broadcastMessage("§b" + owner + " is the §4Owner§b!");
            return true;
        }
        if (player.getName().equals("Telesphoreo") && !owner.equalsIgnoreCase("Telesphoreo"))
        {
            Bukkit.broadcastMessage("§bTelesphoreo is a §5Developer");
            return true;
        }
        if (player.getName().equals("OxLemonxO")) {
            Bukkit.broadcastMessage("§bOxLemonxO is a §5Developer§b and the §4Ki§5ng §6Of §5Le§6mo§7ns§b!");
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
        return true;
    }
}