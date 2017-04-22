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


    public static String buildDate = "4/22/17";
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

    /*public void loadConfiguration() {
        getConfig().options().copyDefaults(true);
        saveConfig();
        //
        String server_spawn_on_join = "server.spawn_on_join"; // spawn on join
        String server_drop_items_on_death = "server.drop_items_on_death"; // drop items on death
        String server_clear_inventory_on_join = "server.clear_inventory_on_join"; // clear inventory on join
        String server_fall_damage = "server.fall_damage_enabled";
        String server_hunger = "server.hunger_enabled";
        String server_dev = "server.dev";
        String splash_potions_enabled = "server.splash_potions_enabled";
        //
        String applications_enabled = "server.applications_enabled";
        String new_thread_link = "server.admin_app_new_thread_link";
        String admin_app_template = "server.admin_application_template";
        //
        String website = "server.website";
        String forums = "server.forums";
        String discord = "server.discord";
        //

        //
        /*
        Remove for now
        String staff_moderator = "staff.moderator";
        String staff_admin = "staff.admin";
        String staff_developer = "staff.developer";
        String staff_builder = "staff.builder";
        String staff_owner = "staff.owner";
        */
        //
        /*String superusers = "players.superusers";
        //
        String launchpads_enabled = "launchpads.enabled";
        String launchpads_bottom_block_id = "launchpads.bottom-block-id";
        //
        getConfig().addDefault(server_spawn_on_join, "false");
        getConfig().addDefault(server_drop_items_on_death, "true");
        getConfig().addDefault(server_clear_inventory_on_join, "false");
        getConfig().addDefault(server_hunger, "true");
        getConfig().addDefault(server_fall_damage, "true");
        getConfig().addDefault(server_dev, "false");
        getConfig().addDefault(splash_potions_enabled, "true");
        //
        getConfig().addDefault(applications_enabled, "false");
        getConfig().addDefault(admin_app_template, "none");
        getConfig().addDefault(new_thread_link, "none");
        //
        getConfig().addDefault(website, "none");
        getConfig().addDefault(forums, "none");
        getConfig().addDefault(discord, "none");
        
        //
        /*
        getConfig().addDefault(staff_moderator, "None");
        getConfig().addDefault(staff_admin, "None");
        getConfig().addDefault(staff_developer, "Telesphoreo, OxLemonxO");
        getConfig().addDefault(staff_builder, "None");
        getConfig().addDefault(staff_owner, "Telesphoreo");
        */
        
        /*getConfig().addDefault(superusers, "Telesphoreo, OxLemonxO");
        //
        getConfig().addDefault(launchpads_enabled, "false");
        getConfig().addDefault(launchpads_bottom_block_id, "152");
        //
        getConfig().options().copyDefaults(true);
        saveConfig();
    }*/
}