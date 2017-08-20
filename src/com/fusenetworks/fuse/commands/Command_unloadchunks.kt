package com.fusenetworks.fuse.commands

import com.fusenetworks.fuse.util.NLog
import com.fusenetworks.fuse.util.NUtil
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.Chunk
import org.bukkit.World

// Credit to TheMinecraft

@CommandPermissions(source = SourceType.BOTH)
@CommandParameters(description = "Unloads unused chunks", usage = "/<command> [-s]", aliases = "rc,uc")
class Command_unloadchunks : BaseCommand() {
    override fun run(sender: CommandSender, sender_p: Player, cmd: Command, commandLabel: String, args: Array<String>, senderIsConsole: Boolean): Boolean {
        var numChunks = 0
        if (!sender.hasPermission("fuse.unloadchunks")) {
            sender.sendMessage(Messages.MSG_NO_PERMS)
            return true
        }
        if (senderIsConsole) {
            numChunks = server.worlds.stream().map { world -> unloadUnusedChunks(world) }.reduce(numChunks, BinaryOperator<Int> { a, b -> Integer.sum(a, b) })
            sender.sendMessage(ChatColor.GRAY.toString() + "Unloading all unused chunks\n"
                    + numChunks + " chunks unloaded")
            return true
        } // end senderIsConsole

        when (args.isEmpty()) {

            numChunks = server.worlds.stream().map { world -> unloadUnusedChunks(world) }.reduce(numChunks, BinaryOperator<Int> { a, b -> Integer.sum(a, b) })
            NUtil.playerMsg(sender, numChunks.toString() + " chunks unloaded")
            NUtil.adminAction(sender.name, "Unloading all unused chunks", false)
            NLog.info(numChunks.toString() + " chunks unloaded")
            return true
        } // end if args are 0
        else if (args[0].equals("-s", ignoreCase = true)) {
            numChunks = server.worlds.stream().map { world -> unloadUnusedChunks(world) }.reduce(numChunks, BinaryOperator<Int> { a, b -> Integer.sum(a, b) })
            NUtil.playerMsg(sender, numChunks.toString() + " chunks unloaded")
            NLog.info(numChunks.toString() + " chunks unloaded")
        } // end silent
        else {
            return false
        } // else
        return true
    } // end

    private fun unloadUnusedChunks(world: World): Int {
        var numChunks = 0
        for (loadedChunk in world.loadedChunks) {
            if (!world.isChunkInUse(loadedChunk.x, loadedChunk.z)) {
                if (world.unloadChunk(loadedChunk)) {
                    numChunks++
                }
            }
        }
        return numChunks
    }
}
