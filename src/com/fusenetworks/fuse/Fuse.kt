package com.fusenetworks.fuse

import com.fusenetworks.fuse.listener.*
import com.fusenetworks.fuse.util.NLog
import com.fusenetworks.fuse.util.NUtil
import org.bukkit.Bukkit
import org.bukkit.Server
import org.bukkit.plugin.java.JavaPlugin

class Fuse : JavaPlugin() {
    internal var jarFile = this.file

    override fun onLoad() {
        NLog.setServerLogger(server.logger)
        NLog.setServerLogger(server.logger)
    }

    override fun onEnable() {
        server.pluginManager.registerEvents(AutoUpdate(), Fuse())
        server.pluginManager.registerEvents(CommandBlocker(), Fuse())
        server.pluginManager.registerEvents(Commandspy(), Fuse())
        server.pluginManager.registerEvents(ConfigListener(), Fuse())
        server.pluginManager.registerEvents(Developer(), Fuse())
        server.pluginManager.registerEvents(Launchpads(), Fuse())
        server.pluginManager.registerEvents(LoginMessages(), Fuse())
        server.pluginManager.registerEvents(NoFall(), Fuse())
        server.pluginManager.registerEvents(NoHunger(), Fuse())
        server.pluginManager.registerEvents(PotionListener(), Fuse())
        server.pluginManager.registerEvents(SignPatch(), Fuse())
        Config.loadConfigs()
        Bukkit.getScheduler().scheduleSyncRepeatingTask(Bukkit.getPluginManager().getPlugin("Fuse"), {
            if (NUtil.NEntityWiper.wipeEntities(true, true) == 1) {
                NLog.info(NUtil.NEntityWiper.wipeEntities(true, true).toString() + " entity removed")
            } else if (NUtil.NEntityWiper.wipeEntities(true, true) != 0) {
                NLog.info(NUtil.NEntityWiper.wipeEntities(true, true).toString() + " entities removed")
            }
        }, 1L, 300.toLong() * 20)
    }

    override fun onDisable() {}

    public companion object {

        var plugin: Fuse? = null
        var server: Server? = null
        var instance: Fuse? = null


        var buildDate = "8/20/17"
        var buildCreator = "Telesphoreo"
    }
}