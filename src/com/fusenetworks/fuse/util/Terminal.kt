package com.fusenetworks.fuse.util

import com.fusenetworks.fuse.Fuse
import org.bukkit.command.CommandSender
import org.bukkit.scheduler.BukkitRunnable
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

object Terminal {

    fun runShellCommandAsync(sender: CommandSender, command: String) {
        object : BukkitRunnable() {
            override fun run() {
                try {
                    runShellCommand(sender, command)
                } catch (ex: IOException) {
                    NLog.severe(ex)
                    if (ex.toString().contains("error=2")) {
                        sender.sendMessage("Cannot run program \"$command\": error=2, No such file or directory")
                    } else {
                        sender.sendMessage("Unknown error while executing command")
                    }
                } catch (ex: InterruptedException) {
                    sender.sendMessage("Error, see logs for more details")
                    NLog.severe(ex)
                }

            }
        }.runTaskAsynchronously(Fuse.plugin)

    }

    @Throws(InterruptedException::class, IOException::class)
    fun runShellCommand(sender: CommandSender, command: String) {
        val proc = Runtime.getRuntime().exec(command)

        // Read the output
        val reader = BufferedReader(InputStreamReader(proc.inputStream))

        var line = ""
        line = reader.readLine()
            while (reader.readLine() != null) {
                sender.sendMessage("root@minecraft_server: " + line)
            }

        proc.waitFor()
    }
}
