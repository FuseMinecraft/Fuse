package com.fusenetworks.fuse.commands

import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

abstract class Command_glassmode : CommandExecutor {
    fun run(sender: CommandSender, sender_p: Player, cmd: Command, commandLabel: String, args: Array<String>, senderIsConsole: Boolean): Boolean {
        if (!sender.hasPermission("fuse.glassmode")) {
            sender.sendMessage(Messages.MSG_NO_PERMS)
            return true
        }
        if (senderIsConsole) {
            sender.sendMessage(Messages.PLAYER_ONLY)
            return true
        }
        if (args.size != 1) {
            return false
        }
        if (args[0] == "enable" || args[0] == "on") {
            val invisible = PotionEffect(PotionEffectType.INVISIBILITY, 99999999 * 20, 3)
            val glow = PotionEffect(PotionEffectType.GLOWING, 99999999 * 20, 3)
            sender_p.addPotionEffect(invisible)
            sender_p.addPotionEffect(glow)
            sender.sendMessage(ChatColor.BLUE.toString() + "Enabled glass mode")
            return true
        }
        if (args[0] == "disable" || args[0] == "off") {
            sender_p.removePotionEffect(PotionEffectType.INVISIBILITY)
            sender_p.removePotionEffect(PotionEffectType.GLOWING)
            sender.sendMessage(ChatColor.BLUE.toString() + "Disabled glass mode")
            return true
        } else {
            return false
        }
    }
}
