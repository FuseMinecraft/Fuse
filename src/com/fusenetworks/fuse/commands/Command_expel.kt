package com.fusenetworks.fuse.commands

import com.fusenetworks.fuse.util.NUtil
import org.apache.commons.lang3.StringUtils
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.util.*

abstract class Command_expel : CommandExecutor {
    fun run(sender: CommandSender, sender_p: Player, cmd: Command, commandLabel: String, args: Array<String>, senderIsConsole: Boolean): Boolean {
        if (!sender.hasPermission("fuse.expel")) {
            sender.sendMessage(Messages.MSG_NO_PERMS)
            return true
        }
        if (senderIsConsole) {
            sender.sendMessage(Messages.PLAYER_ONLY)
            return true
        }
        var radius = 20.0
        var strength = 5.0

        if (args.size >= 1) {
            try {
                radius = Math.max(1.0, Math.min(100.0, java.lang.Double.parseDouble(args[0])))
            } catch (ex: NumberFormatException) {
            }

        }

        if (args.size >= 2) {
            try {
                strength = Math.max(0.0, Math.min(50.0, java.lang.Double.parseDouble(args[1])))
            } catch (ex: NumberFormatException) {
            }

        }

        val pushedPlayers = ArrayList<String>()

        val senderPos = sender_p.location.toVector()
        val players = sender_p.world.players
        for (player in players) {
            if (player == sender_p) {
                continue
            }

            val targetPos = player.location
            val targetPosVec = targetPos.toVector()

            var inRange = false
            try {
                inRange = targetPosVec.distanceSquared(senderPos) < radius * radius
            } catch (ex: IllegalArgumentException) {
            }

            if (inRange) {
                player.world.createExplosion(targetPos, 0.0f, false)
                NUtil.setFlying(player, false)
                player.velocity = targetPosVec.subtract(senderPos).normalize().multiply(strength)
                pushedPlayers.add(player.name)
            }
        }

        if (pushedPlayers.isEmpty()) {
            sender.sendMessage(ChatColor.GRAY.toString() + "No players pushed.")
        } else if (pushedPlayers.size == 1) {
            sender.sendMessage(ChatColor.GRAY.toString() + "Pushed " + pushedPlayers.size + " player: " + StringUtils.join(pushedPlayers, ""))
        } else {
            sender.sendMessage(ChatColor.GRAY.toString() + "Pushed " + pushedPlayers.size + " players: " + StringUtils.join(pushedPlayers, ", "))
        }

        return true
    }
}
