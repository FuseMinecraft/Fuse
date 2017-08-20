package com.fusenetworks.fuse.commands

import com.fusenetworks.fuse.Fuse
import com.fusenetworks.fuse.Updater
import org.bukkit.Bukkit.getServer
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.ChatColor
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.PluginDescriptionFile
import org.bukkit.plugin.PluginManager

@CommandPermissions(source = SourceType.BOTH)
@CommandParameters(description = "Shows information about Fuse", usage = "/<command> [reload | debug | help | update]", aliases = "packscore,oxygen,sky,nitrogen,trident")
class Command_fuse : BaseCommand() {
    override fun run(sender: CommandSender, sender_p: Player, cmd: Command, commandLabel: String, args: Array<String>, senderIsConsole: Boolean): Boolean {
        val spawn_on_join = plugin.config.getString("server.spawn_on_join")
        val applications_enabled = plugin.config.getString("server.applications_enabled")
        val op_kits = plugin.config.getString("server.op_kits")
        val drop_items_on_death = plugin.config.getString("server.drop_items_on_death")
        val clear_inventory_on_join = plugin.config.getString("server.clear_inventory_on_join")
        val server_hunger = plugin.config.getString("server.hunger_enabled")
        val fall_damage_enabled = plugin.config.getString("server.fall_damage_enabled")
        val dev = plugin.config.getString("server.dev")
        val superusers = plugin.config.getString("players.superusers")
        val pm = server.pluginManager
        val p = pm.getPlugin("Fuse")
        val pdf = p.description
        val version = pdf.version
        if (args.size == 0) {
            sender.sendMessage(ChatColor.GOLD.toString() + plugin.name)
            sender.sendMessage(ChatColor.GOLD.toString() + "Base Version: 1.3.1.1")
            sender.sendMessage(ChatColor.RED.toString() + "Compiled on " + Fuse.buildDate + " by " + Fuse.buildCreator)
            sender.sendMessage(ChatColor.RED.toString() + "Fuse is an advanced plugin designed to provide useful utilities for a Minecraft server")
            sender.sendMessage(ChatColor.GREEN.toString() + "Type /fuse help for command usage")
            sender.sendMessage(ChatColor.GREEN.toString() + "Type /contributors to see who contributed to Fuse")
            sender.sendMessage(ChatColor.GREEN.toString() + "Type /fuse update to check for and install updates.")
            if (dev == "true") {
                sender.sendMessage(ChatColor.DARK_AQUA.toString() + "The server is currently in development mode. "
                        + "This means there may be unstable plugin builds on this server, and the server could crash more than normal!")
            }
            return true
        }
        when (args[0].toLowerCase()) {
            "reload" -> {
                if (!sender.hasPermission("fuse.reload")) {
                    sender.sendMessage(Messages.MSG_NO_PERMS)
                    return true
                }
                plugin.reloadConfig()
                sender.sendMessage(ChatColor.GRAY.toString() + "Configuration file has been successfully reloaded!")
                return true
            }
            "debug" -> {
                if (!sender.hasPermission("fuse.debug")) {
                    sender.sendMessage(Messages.MSG_NO_PERMS)
                    return true
                }
                sender.sendMessage(ChatColor.GRAY.toString() + "Config Options")
                sender.sendMessage(ChatColor.GRAY.toString() + "server.spawn_on_join: " + spawn_on_join)
                sender.sendMessage(ChatColor.GRAY.toString() + "server.applications_enabled: " + applications_enabled)
                sender.sendMessage(ChatColor.GRAY.toString() + "server.op_kits: " + op_kits)
                sender.sendMessage(ChatColor.GRAY.toString() + "server.dev: " + dev)
                sender.sendMessage(ChatColor.GRAY.toString() + "server.drop_items_on_death: " + drop_items_on_death)
                sender.sendMessage(ChatColor.GRAY.toString() + "server.clear_inventory_on_join: " + clear_inventory_on_join)
                sender.sendMessage(ChatColor.GRAY.toString() + "server.hunger_enabled: " + server_hunger)
                sender.sendMessage(ChatColor.GRAY.toString() + "server.fall_damage_enabled: " + fall_damage_enabled)
                sender.sendMessage(ChatColor.GRAY.toString() + "players.superusers: " + superusers)
                return true
            } // debug end
            "help" -> {
                if (!sender.hasPermission("fuse.extendedhelp")) {
                    sender.sendMessage(ChatColor.GOLD.toString() + "Fuse Help")
                    sender.sendMessage(ChatColor.GOLD.toString() + "/admininfo - Tells you how to apply for admin")
                    sender.sendMessage(ChatColor.GOLD.toString() + "/contributors - Lists Fuse Networks's contributors")
                    sender.sendMessage(ChatColor.GOLD.toString() + "/discord - Gives a link to the Discord server")
                    sender.sendMessage(ChatColor.GOLD.toString() + "/forums - Gives a link to the forums")
                    sender.sendMessage(ChatColor.GOLD.toString() + "/fw - Shoots a firework into the sky")
                    sender.sendMessage(ChatColor.GOLD.toString() + "/glassmode - Makes you invisible and adds a glow effect")
                    sender.sendMessage(ChatColor.GOLD.toString() + "/rename - Renames the item in your hand")
                    sender.sendMessage(ChatColor.GOLD.toString() + "/fuse - Shows information about Fuse")
                    sender.sendMessage(ChatColor.GOLD.toString() + "/website - Gives you a link to the website")
                    return true
                } else {
                    sender.sendMessage(ChatColor.GOLD.toString() + "Fuse Help")
                    sender.sendMessage(ChatColor.RED.toString() + "/adminchat - Talk privatley with other admins")
                    sender.sendMessage(ChatColor.GOLD.toString() + "/admininfo - Tells you how to apply for admin")
                    sender.sendMessage(ChatColor.RED.toString() + "/clearlag [-s] - Clears lag")
                    sender.sendMessage(ChatColor.RED.toString() + "/commandspy - Toggles commandspy")
                    sender.sendMessage(ChatColor.GOLD.toString() + "/contributors - Lists Fuse Networks's contributors")
                    sender.sendMessage(ChatColor.GOLD.toString() + "/discord - Gives a link to the Discord server")
                    sender.sendMessage(ChatColor.RED.toString() + "/expel - Push players away from you")
                    sender.sendMessage(ChatColor.GOLD.toString() + "/forums - Gives a link to the forums")
                    sender.sendMessage(ChatColor.GOLD.toString() + "/fw - Shoots a firework into the sky")
                    sender.sendMessage(ChatColor.RED.toString() + "/gcmd - Makes another player execute a command")
                    sender.sendMessage(ChatColor.GOLD.toString() + "/glassmode - Makes you invisible and glowing")
                    sender.sendMessage(ChatColor.GOLD.toString() + "/information - Gives you information about the server you're currently on")
                    sender.sendMessage(ChatColor.GOLD.toString() + "/rename - Renames the item in your hand")
                    sender.sendMessage(ChatColor.RED.toString() + "/ship - Ship a player with another player")
                    sender.sendMessage(ChatColor.GOLD.toString() + "/fuse - Shows information about Fuse")
                    sender.sendMessage(ChatColor.RED.toString() + "/unloadchunks [-s] - Unloads all unused chunks")
                    sender.sendMessage(ChatColor.GOLD.toString() + "/website - Gives you a link to the website")
                    return true
                }
            }
            "update" -> {
                if (!sender.hasPermission("fuse.update")) {
                    sender.sendMessage(Messages.MSG_NO_PERMS)
                    return true
                }
                val updater = Updater(Fuse.plugin)
                updater.update(sender)
                return true
            }
            else -> {
                return false
            }
        } // end args
    }
}

