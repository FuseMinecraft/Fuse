package com.fusenetworks.fuse.commands

import java.util.Random
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.ChatColor
import org.bukkit.Location

@CommandPermissions(source = SourceType.BOTH)
@CommandParameters(description = "Teleports a player to a random location", usage = "/<command>", aliases = "tpr")
class Command_tprandom : BaseCommand() {
    override fun run(sender: CommandSender, sender_p: Player, cmd: Command, commandLabel: String, args: Array<String>, senderIsConsole: Boolean): Boolean {
        if (!sender.hasPermission("fuse.tprandom")) {
            sender.sendMessage(Messages.MSG_NO_PERMS)
            return true
        }
        val location = plugin.config.getString("server.location")
        if (location.equals("factions", ignoreCase = true)) {
            if (sender is Player) {
                val r = Random()
                val x = sender_p.location.blockX + r.nextInt(1000)
                val z = sender_p.location.blockZ + r.nextInt(1000)
                val l = Location(sender_p.location.world, x.toDouble(), sender_p.location.blockY.toDouble(), z.toDouble())
                sender_p.teleport(l)
                sender.sendMessage(ChatColor.LIGHT_PURPLE.toString() + "Poof!")
            } else {
                sender.sendMessage("That command is for players only!")
            }
        } else {
            sender.sendMessage(Messages.INVALID_SERVER)
        }
        return true
    }
}
