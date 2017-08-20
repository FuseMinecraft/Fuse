package com.fusenetworks.fuse.commands

import com.fusenetworks.fuse.util.NUtil
import org.bukkit.Material
import org.apache.commons.lang.StringUtils
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta

@CommandPermissions(source = SourceType.BOTH)
@CommandParameters(description = "Renames the item in your hand", usage = "/<command> <name>")
class Command_rename : BaseCommand() {
    override fun run(sender: CommandSender, sender_p: Player, cmd: Command, commandLabel: String, args: Array<String>, senderIsConsole: Boolean): Boolean {
        if (senderIsConsole) {
            sender.sendMessage(Messages.PLAYER_ONLY)
            return true
        }
        if (args.size < 1) {
            return false
        }
        val itemRaw = StringUtils.join(args, " ")
        val itemName = NUtil.colorize(itemRaw)
        val i = sender_p.itemInHand
        if (sender_p.itemInHand.type == Material.AIR) {
            sender.sendMessage(ChatColor.RED.toString() + "You cannot rename this item")
            return true
        } else {
            val im = i.itemMeta
            im.displayName = StringUtils.replace(itemName, "_", " ")
            i.itemMeta = im
            sender.sendMessage(ChatColor.GRAY.toString() + "Item renamed to: " + itemRaw)
        }
        return true
    }
}