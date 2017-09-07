package com.fusenetworks.fuse.listener;

import static com.fusenetworks.fuse.Fuse.plugin;

import com.fusenetworks.fuse.util.NUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginMessages implements Listener {
    @EventHandler
    public boolean onPlayerJoin(PlayerJoinEvent event) {
        String owner = plugin.getConfig().getString("server.owner");
        Player player = event.getPlayer();
        if (player.getName().equals(owner)) {
            Bukkit.broadcastMessage("§b" + owner + " is the §4Owner§b!");
            return true;
        }
        if (player.getName().equals("OxLemonxO")) {
            Bukkit.broadcastMessage("§bOxLemonxO is a §5Developer§b and the §4Ki§5ng §6Of §5Le§6mo§7ns§b!");
            return true;
        }
        if (player.getName().equals("xShotzfired_")) {
            Bukkit.broadcastMessage("§bxShotzfired_ is an §9Admin §band §7The Glock King§b!");
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