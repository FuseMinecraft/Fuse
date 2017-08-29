package com.fusenetworks.fuse

import com.fusenetworks.fuse.commands.Adminchat
import com.fusenetworks.fuse.util.NLog
import com.fusenetworks.fuse.util.NUtil
import org.bukkit.Bukkit
import org.bukkit.Server
import org.bukkit.plugin.java.JavaPlugin

class Fuse : JavaPlugin() {
    internal var jarFile = this.file

    override fun onLoad() {
        plugin = this
        Fuse.server = plugin!!.server
        NLog.setServerLogger(server.logger)
        NLog.setServerLogger(server.logger)
    }

    override fun onEnable() {
        /*server.getPluginManager().registerEvents(new AutoUpdate(), Fuse.plugin);
        server.getPluginManager().registerEvents(new CommandBlocker(), Fuse.plugin);
        server.getPluginManager().registerEvents(new Commandspy(), Fuse.plugin);
        server.getPluginManager().registerEvents(new ConfigListener(), Fuse.plugin);
        server.getPluginManager().registerEvents(new Developer(), Fuse.plugin);
        server.getPluginManager().registerEvents(new Launchpads(), Fuse.plugin);
        server.getPluginManager().registerEvents(new LoginMessages(), Fuse.plugin);
        server.getPluginManager().registerEvents(new NoFall(), Fuse.plugin);
        server.getPluginManager().registerEvents(new NoHunger(), Fuse.plugin);
        server.getPluginManager().registerEvents(new PotionListener(), Fuse.plugin);
        server.getPluginManager().registerEvents(new SignPatch(), Fuse.plugin);*/
        getCommand("adminchat").executor = Adminchat
        Config.loadConfigs()
        Bukkit.getScheduler().scheduleSyncRepeatingTask(Bukkit.getPluginManager().getPlugin("Fuse"), {
            if (NUtil.NEntityWiper.wipeEntities(true, true) == 1) {
                NLog.info(NUtil.NEntityWiper.wipeEntities(true, true).toString() + " entity removed")
            } else if (NUtil.NEntityWiper.wipeEntities(true, true) != 0) {
                NLog.info(NUtil.NEntityWiper.wipeEntities(true, true).toString() + " entities removed")
            }
        }, 1L, 300.toLong() * 20)
        instance = this
    }

    override fun onDisable() {}

    companion object {
        var plugin: Fuse? = null
        var server: Server? = null
        var instance: Fuse? = null


        var buildDate = "8/28/17"
        var buildCreator = "Telesphoreo"
    }
}