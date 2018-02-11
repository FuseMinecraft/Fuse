package com.fusenetworks.fuse.listener;

import com.fusenetworks.fuse.Fuse;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.bukkit.Bukkit.getServer;

public class AutoUpdate implements Listener {

    final String versionLink = "http://flowdesigns.us/version.txt";
    private Plugin plugin;

    @EventHandler
    public boolean onPlayerJoin(PlayerJoinEvent event) {
        if (event.getPlayer().hasPermission("fuse.update") || event.getPlayer().isOp()) {
            com.fusenetworks.fuse.Updater updater = new com.fusenetworks.fuse.Updater(Fuse.plugin);
            int oldVersion = this.getVersionFromString(plugin.getDescription().getVersion());
            String path = updater.getFilePath();
            try {
                URL url = new URL(versionLink);
                URLConnection con = url.openConnection();
                InputStreamReader isr = new InputStreamReader(con.getInputStream());
                BufferedReader reader = new BufferedReader(isr);
                reader.ready();
                int newVersion = this.getVersionFromString(reader.readLine());
                reader.close();


                event.getPlayer().sendMessage(ChatColor.RED + "There is an update available for Fuse (" + newVersion + " from " + oldVersion + "_To update Fuse, type /fuse update");
                event.getPlayer().sendMessage(ChatColor.RED + "There is an update available for Fuse (" + newVersion + " from " + oldVersion + "_To update Fuse, type /fuse update");

                if (newVersion > oldVersion) {
                    event.getPlayer().sendMessage(ChatColor.RED + "There is an update available for Fuse (" + newVersion + " from " + oldVersion + "_To update Fuse, type /fuse update");
                }
            } catch (IOException e) {
                plugin.getLogger().log(Level.SEVERE, "Failed to automatically check for updates", e);
            }
        }
        return true;
    }
    public int getVersionFromString(String from)
    {
        String result = "";
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(from);

        while(matcher.find())
        {
            result += matcher.group();
        }

        return result.isEmpty() ? 0 : Integer.parseInt(result);
    }
}