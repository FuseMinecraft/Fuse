package com.fusenetworks.fuse;

import com.fusenetworks.fuse.listener.SignPatch;
import com.fusenetworks.fuse.listener.NoHunger;
import com.fusenetworks.fuse.listener.Developer;
import com.fusenetworks.fuse.listener.NoFall;
import com.fusenetworks.fuse.listener.Commandspy;
import com.fusenetworks.fuse.listener.Launchpads;
import com.fusenetworks.fuse.listener.ConfigListener;
import com.fusenetworks.fuse.listener.PotionListener;
import com.fusenetworks.fuse.listener.LoginMessages;
import com.fusenetworks.fuse.commands.CMD_Handler;
import com.fusenetworks.fuse.commands.CMD_Loader;
import com.fusenetworks.fuse.listener.CommandBlocker;
import com.fusenetworks.fuse.util.NLog;
import com.fusenetworks.fuse.util.NUtil;
import java.io.File;
import java.io.IOException;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.mcstats.Metrics;

public class Fuse extends JavaPlugin {

    public static Fuse plugin;
    public static Server server;
    public static Fuse instance;


    public static String buildDate = "6/10/17";
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
        server.getPluginManager().registerEvents(new CommandBlocker(), Fuse.plugin);
        server.getPluginManager().registerEvents(new Commandspy(), Fuse.plugin);
        server.getPluginManager().registerEvents(new ConfigListener(), Fuse.plugin);
        server.getPluginManager().registerEvents(new Developer(), Fuse.plugin);
        server.getPluginManager().registerEvents(new Launchpads(), Fuse.plugin);
        server.getPluginManager().registerEvents(new LoginMessages(), Fuse.plugin);
        server.getPluginManager().registerEvents(new NoFall(), Fuse.plugin);
        server.getPluginManager().registerEvents(new NoHunger(), Fuse.plugin);
        server.getPluginManager().registerEvents(new PotionListener(), Fuse.plugin);
        server.getPluginManager().registerEvents(new SignPatch(), Fuse.plugin);
        Config.loadConfigs();
        new BukkitRunnable() {
            @Override
            public void run() {
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