package com.fusenetworks.fuse.listener;

import static com.fusenetworks.fuse.Fuse.plugin;

import com.fusenetworks.fuse.Fuse;
import com.oracle.deploy.update.Updater;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;

public class AutoUpdate implements Listener {
    final String versionLink = "http://vps76574.vps.ovh.ca/version.txt";

    @EventHandler
    public boolean onPlayerJoin(PlayerJoinEvent event) {
            com.fusenetworks.fuse.Updater updater = new com.fusenetworks.fuse.Updater(Fuse.plugin);
            int oldVersion = updater.getVersionFromString(plugin.getDescription().getVersion());
            String path = updater.getFilePath();

            try {
                URL url = new URL(versionLink);
                URLConnection con = url.openConnection();
                InputStreamReader isr = new InputStreamReader(con.getInputStream());
                BufferedReader reader = new BufferedReader(isr);
                reader.ready();
                int newVersion = updater.getVersionFromString(reader.readLine());
                reader.close();

                if (newVersion > oldVersion) {
                    if (event.getPlayer().hasPermission("fuse.update") || event.getPlayer().isOp()) {
                        event.getPlayer().sendMessage(ChatColor.RED + "There is an update available for Fuse. To update Fuse, type /fuse update");
                    }
                }
            } catch (IOException e) {
                plugin.getLogger().log(Level.SEVERE, "Failed to automatically check for updates", e);
            }
        return true;
        }
}