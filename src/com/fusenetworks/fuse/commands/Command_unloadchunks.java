package com.fusenetworks.fuse.commands;

import com.fusenetworks.fuse.util.NLog;
import com.fusenetworks.fuse.util.NUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.Chunk;
import org.bukkit.World;

// Credit to TheMinecraft

public abstract class Command_unloadchunks implements CommandExecutor {
    public boolean run(final CommandSender sender, final Player sender_p, final Command cmd, final String commandLabel, final String[] args, final boolean senderIsConsole)
    {
        int numChunks = 0;
        if (!sender.hasPermission("fuse.unloadchunks")) {
            sender.sendMessage(ChatColor.GOLD + "Fuse >> You do not have permission to do this");
            return true;
        }
        if (senderIsConsole)
        {
            numChunks = Bukkit.getWorlds().stream().map((world) -> unloadUnusedChunks(world)).reduce(numChunks, Integer::sum);
            sender.sendMessage(ChatColor.GRAY + "Unloading all unused chunks\n"
                    + numChunks + " chunks unloaded");
            return true;
        } // end senderIsConsole

        if (args.length == 0) {

            numChunks = Bukkit.getWorlds().stream().map((world) -> unloadUnusedChunks(world)).reduce(numChunks, Integer::sum);
            NUtil.playerMsg(sender, numChunks + " chunks unloaded");
            NUtil.adminAction(sender.getName(), "Unloading all unused chunks", false);
            NLog.info(numChunks + " chunks unloaded");
            return true;
        } // end if args are 0
        else if (args[0].equalsIgnoreCase("-s"))
        {
            numChunks = Bukkit.getWorlds().stream().map((world) -> unloadUnusedChunks(world)).reduce(numChunks, Integer::sum);
            NUtil.playerMsg(sender, numChunks + " chunks unloaded");
            NLog.info(numChunks + " chunks unloaded");
        } // end silent
        else {
            return false;
        } // else
        return true;
    } // end

    private int unloadUnusedChunks(World world)
    {
        int numChunks = 0;
        for (Chunk loadedChunk : world.getLoadedChunks())
        {
            if (!world.isChunkInUse(loadedChunk.getX(), loadedChunk.getZ()))
            {
                if (world.unloadChunk(loadedChunk))
                {
                    numChunks++;
                }
            }
        }
        return numChunks;
    }
}