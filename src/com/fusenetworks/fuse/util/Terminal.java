package com.fusenetworks.fuse.util;

import com.fusenetworks.fuse.Fuse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.bukkit.command.CommandSender;
import org.bukkit.scheduler.BukkitRunnable;

public class Terminal {
    
    public static void runShellCommandAsync(CommandSender sender, String command)
    {
        new BukkitRunnable()
        {
            @Override
            public void run()
            {
            try {
            runShellCommand(sender, command);
            }
            catch (IOException ex) {
                NLog.severe(ex);
                if (ex.toString().contains("error=2"))
                {
                    sender.sendMessage("Cannot run program \"" + command + "\": error=2, No such file or directory");
                } else {
                    sender.sendMessage("Unknown error while executing command");
                }
            }
            catch (InterruptedException ex) {
                sender.sendMessage("Error, see logs for more details");
                NLog.severe(ex);
                }
            }
        }.runTaskAsynchronously(Fuse.plugin);
     
    }
 
    public static void runShellCommand(CommandSender sender, String command) throws InterruptedException, IOException
    {
        Process proc = Runtime.getRuntime().exec(command);
 
        // Read the output
        BufferedReader reader
                = new BufferedReader(new InputStreamReader(proc.getInputStream()));
 
        String line = "";
        while ((line = reader.readLine()) != null)
        {
            sender.sendMessage("minecraft@vps76574: " + line);
        }
 
        proc.waitFor();
    }
    
}
