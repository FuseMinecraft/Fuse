package com.fusenetworks.fuse.commands

import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.ChatColor

@CommandPermissions(source = SourceType.BOTH)
@CommandParameters(description = "Shows the people who contributed to Fuse", usage = "/<command>", aliases = "contributers")
class Command_contributors : BaseCommand() {
    override fun run(sender: CommandSender, sender_p: Player, cmd: Command, commandLabel: String, args: Array<String>, senderIsConsole: Boolean): Boolean {
        sender.sendMessage(ChatColor.RED.toString() + "Contributors to Fuse:\n" +
                "Telesphoreo - Developer\n" +
                "OxLemonxO - Developer\n" +
                "Madgeek1450, Prozza - TotalFreedomMod code\n" +
                "TheMinecraft (_Windows) - Unload chunks command")
        return true
    }
}
