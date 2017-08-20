package com.fusenetworks.fuse.commands

import com.fusenetworks.fuse.Fuse
import org.bukkit.Server
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

// Credit to TF

abstract class BaseCommand {
    protected var plugin: Fuse
    protected var server: Server
    private var commandSender: CommandSender? = null
    private var commandClass: Class<*>? = null

    abstract fun run(sender: CommandSender, sender_p: Player, cmd: Command, commandLabel: String, args: Array<String>, senderIsConsole: Boolean): Boolean

    fun setup(plugin: Fuse, commandSender: CommandSender, commandClass: Class<*>) {
        this.plugin = plugin
        this.server = plugin.server
        this.commandSender = commandSender
        this.commandClass = commandClass
    }

    @JvmOverloads
    fun getPlayer(partialName: String?, exact: Boolean = false): Player? {
        if (partialName == null || partialName.isEmpty()) {
            return null
        }

        val players = server.onlinePlayers

        // Check exact matches first.
        for (player in players) {
            if (partialName.equals(player.name, ignoreCase = true)) {
                return player
            }
        }

        if (exact) {
            return null
        }

        // Then check partial matches in name.
        for (player in players) {
            if (player.name.toLowerCase().contains(partialName.toLowerCase())) {
                return player
            }
        }

        for (player in players) {
            if (player.displayName.toLowerCase().contains(partialName.toLowerCase())) {
                return player
            }
        }

        return null
    }
}