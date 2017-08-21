package com.fusenetworks.fuse.commands

import org.bukkit.ChatColor
import org.bukkit.Color
import org.bukkit.FireworkEffect
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.EntityType
import org.bukkit.entity.Firework
import org.bukkit.entity.Player
import java.util.*

abstract class Command_fw : CommandExecutor {

    private val rnd = Random()
    private val colors = arrayOf(Color.AQUA, Color.BLACK, Color.BLUE, Color.FUCHSIA, Color.GREEN, Color.LIME, Color.MAROON, Color.OLIVE, Color.ORANGE, Color.PURPLE, Color.RED, Color.TEAL, Color.WHITE, Color.YELLOW)

    fun run(sender: CommandSender, sender_p: Player, cmd: Command, commandLabel: String, args: Array<String>, senderIsConsole: Boolean): Boolean {

        if (senderIsConsole) {
            sender.sendMessage(Messages.PLAYER_ONLY)
            return true
        }

        val p = sender as Player
        val loc = p.location
        if (loc != null) {
            val w = loc.world
            val fw = w.spawnEntity(loc, EntityType.FIREWORK) as Firework
            val fm = fw.fireworkMeta
            val effect = FireworkEffect.builder()
            effect.flicker(this.rnd.nextInt(10) > 2)
            effect.trail(this.rnd.nextInt(10) > 2)
            val types = FireworkEffect.Type.values()
            effect.with(types[this.rnd.nextInt(types.size)])
            val colorcnt = this.rnd.nextInt(3) + 2
            for (i in 0..colorcnt - 1) {
                effect.withColor(this.colors[this.rnd.nextInt(this.colors.size)])
            }
            fm.addEffect(effect.build())
            fm.power = 1
            fw.fireworkMeta = fm
            sender.sendMessage(ChatColor.GRAY.toString() + "You shot a firework in the sky!")
            return true
        }
        if (loc == null) {
            sender.sendMessage(ChatColor.RED.toString() + "Invalid location")
        }
        return true
    }
}