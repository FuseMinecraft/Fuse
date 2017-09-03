package com.fusenetworks.fuse

import com.fusenetworks.fuse.commands.*
import com.fusenetworks.fuse.commands.Commandspy
import com.fusenetworks.fuse.listener.*
import com.fusenetworks.fuse.listener.Developer
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
        Bukkit.getPluginManager().registerEvents(AutoUpdate, this);
        Bukkit.getPluginManager().registerEvents(CommandBlocker(), this);
        Bukkit.getPluginManager().registerEvents(Commandspy, this);
        Bukkit.getPluginManager().registerEvents(ConfigListener(), this);
        Bukkit.getPluginManager().registerEvents(Developer, this);
        Bukkit.getPluginManager().registerEvents(Launchpads(), this);
        Bukkit.getPluginManager().registerEvents(LoginMessages(), this);
        Bukkit.getPluginManager().registerEvents(NoFall, this);
        Bukkit.getPluginManager().registerEvents(NoHunger, this);
        Bukkit.getPluginManager().registerEvents(PotionListener(), this);
        Bukkit.getPluginManager().registerEvents(SignPatch, this);
        getCommand("adminchat").executor = Adminchat
        getCommand("admininfo").executor = Admininfo
        getCommand("clearlag").executor = Clearlag
        getCommand("commandspy").executor = Commandspy
        getCommand("consolesay").executor = Consolesay
        getCommand("contributors").executor = Contributors
        getCommand("developer").executor = com.fusenetworks.fuse.commands.Developer
        getCommand("discord").executor = Discord
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


        var buildDate = "9/2/17"
        var buildCreator = "Telesphoreo"
    }
}