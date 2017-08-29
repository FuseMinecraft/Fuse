package com.fusenetworks.fuse.commands

import org.bukkit.command.CommandExecutor
import org.bukkit.plugin.Plugin

class CommandListener(val plugin: Plugin) : CommandExecutor {

    val controllerManager = BotControllerManager(plugin)

    init {
        controllerManager.registerController(FunCommandsController(plugin), minecraftExclusive = true)
        controllerManager.registerController(UtilCommandsController(plugin), minecraftExclusive = true)
    }

    /**
     * Callback for a captured command event
     *
     * @param sender the sender of the command
     * @param command the command that was captured
     * @param label no idea lol
     * @param args an array of argument strings passed to the command
     * @return whether the command "succeeded". this returns false, the default usage message will be sent to the sender
     */
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        return controllerManager.dispatchMessage(MinecraftCommandWrapper(sender, command, args))
    }