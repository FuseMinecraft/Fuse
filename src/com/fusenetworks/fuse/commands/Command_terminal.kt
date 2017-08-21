package com.fusenetworks.fuse.commands

import com.fusenetworks.fuse.util.Terminal.runShellCommandAsync
import org.apache.commons.lang3.StringUtils
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

abstract class Command_terminal : CommandExecutor {
    fun run(sender: CommandSender, sender_p: Player, cmd: Command, commandLabel: String, args: Array<String>, senderIsConsole: Boolean): Boolean {
        val superusers = Bukkit.getPluginManager().getPlugin("Fuse").config.getString("players.superusers")
        if (superusers != sender.name) {
            sender.sendMessage(Messages.MSG_NO_PERMS)
            return true
        }
        if (args.size == 0) {
            sender.sendMessage("/terminal <command>")
            return true
        }
        runShellCommandAsync(sender, StringUtils.join(args, " "))
        return true
    }

}