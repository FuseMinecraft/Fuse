package com.fusenetworks.fuse;

import com.fusenetworks.fuse.commands.CMD_Handler;
import com.fusenetworks.fuse.commands.CMD_Loader;
import com.fusenetworks.fuse.exploits.Potion;
import com.fusenetworks.fuse.exploits.Sign;
import com.fusenetworks.fuse.listener.*;
import com.fusenetworks.fuse.util.NLog;
import com.fusenetworks.fuse.util.NUtil;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.mcstats.Metrics;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Fuse extends JavaPlugin {

    public static Fuse plugin;
    public static Server server;
    public static Fuse instance;


    public static String buildDate = "2/10/18";
    public static String buildCreator = "Telesphoreo";
    File jarFile = this.getFile();
    
    @Override
    public void onLoad() {
        Fuse.plugin = this;
        Fuse.server = plugin.getServer();
        NLog.setServerLogger(server.getLogger());
        NLog.setServerLogger(server.getLogger());
    }

    @Override
    public void onEnable() {
        server.getPluginManager().registerEvents(new AutoUpdate(), Fuse.plugin);
        server.getPluginManager().registerEvents(new CommandBlocker(), Fuse.plugin);
        server.getPluginManager().registerEvents(new Commandspy(), Fuse.plugin);
        server.getPluginManager().registerEvents(new ConfigListener(), Fuse.plugin);
        server.getPluginManager().registerEvents(new Developer(), Fuse.plugin);
        server.getPluginManager().registerEvents(new Launchpads(), Fuse.plugin);
        server.getPluginManager().registerEvents(new LoginMessages(), Fuse.plugin);
        server.getPluginManager().registerEvents(new NoFall(), Fuse.plugin);
        server.getPluginManager().registerEvents(new NoHunger(), Fuse.plugin);
        server.getPluginManager().registerEvents(new Potion(), Fuse.plugin);
        server.getPluginManager().registerEvents(new PotionListener(), Fuse.plugin);
        server.getPluginManager().registerEvents(new Sign(), Fuse.plugin);
        Config.loadConfigs();
        new BukkitRunnable() {
            @Override
            public void run() {
                CMD_Loader.getCommandMap();
                CMD_Loader.scan();
            }
        }.runTaskLater(plugin, 20L);
        Bukkit.getScheduler().scheduleSyncRepeatingTask(Bukkit.getPluginManager().getPlugin("Fuse"), () -> {
            if ((NUtil.NEntityWiper.wipeEntities(true, true)) == 1)
            {
                NLog.info((NUtil.NEntityWiper.wipeEntities(true, true)) + " entity removed");
            } else if ((NUtil.NEntityWiper.wipeEntities(true, true)) != 0) {
                NLog.info((NUtil.NEntityWiper.wipeEntities(true, true)) + " entities removed");
            }
        }, 1L , (long) 300 * 20);
    instance = this;
    
    try {
    Metrics metrics = new Metrics(this);
    metrics.start();
    } catch (IOException e) {
        // Failed to submit the stats :-(
    }
    
    }

    @Override
    public void onDisable() {
    }

    public static Fuse getInstance() {
        return instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        return CMD_Handler.handleCommand(sender, cmd, commandLabel, args);
    }
}