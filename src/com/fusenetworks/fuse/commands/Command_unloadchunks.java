package com.fusenetworks.fuse.commands;

import com.fusenetworks.fuse.util.NLog;
import com.fusenetworks.fuse.util.NUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.Chunk;
import org.bukkit.World;

// Credit to TheMinecraft

@CommandPermissions(source = SourceType.BOTH)
@CommandParameters(description = "Unloads unused chunks", usage = "/<command> [-s]", aliases = "rc,uc")
public class Command_unloadchunks extends BaseCommand {
    @Override
    public boolean run(final CommandSender sender, final Player sender_p, final Command cmd, final String commandLabel, final String[] args, final boolean senderIsConsole)
    {
        int numChunks = 0;
        if (!sender.hasPermission("fuse.unloadchunks")) {
            sender.sendMessage(Messages.MSG_NO_PERMS);
            return true;
        }
        if (senderIsConsole)
        {
            numChunks = server.getWorlds().stream().map((world) -> unloadUnusedChunks(world)).reduce(numChunks, Integer::sum);
            sender.sendMessage(ChatColor.GRAY + "Unloading all unused chunks\n"
            + numChunks + " chunks unloaded");
            return true;
        } // end senderIsConsole
        
        if (args.length == 0) {

        numChunks = server.getWorlds().stream().map((world) -> unloadUnusedChunks(world)).reduce(numChunks, Integer::sum);
            NUtil.playerMsg(sender, numChunks + " chunks unloaded");
            NUtil.adminAction(sender.getName(), "Unloading all unused chunks", false);
            NLog.info(numChunks + " chunks unloaded");
            return true;
        } // end if args are 0
        else if (args[0].equalsIgnoreCase("-s"))
        {
        numChunks = server.getWorlds().stream().map((world) -> unloadUnusedChunks(world)).reduce(numChunks, Integer::sum);
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
