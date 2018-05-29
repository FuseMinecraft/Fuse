package us.flowdesigns.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import us.flowdesigns.utils.PlayerData;

// Credit to TFM devs

@CommandPermissions(source = SourceType.ONLY_IN_GAME)
@CommandParameters(description = "Spy on other players commands", usage = "/<command>", aliases = "cmdspy")
public class Command_commandspy extends BaseCommand {
    @Override
    public boolean run(CommandSender sender, Player sender_p, Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
    {
    if (senderIsConsole)
    {
        sender.sendMessage(Messages.PLAYER_ONLY);
        return true;
    }
    if (!sender.hasPermission("fuse.commandspy")) {
        sender.sendMessage(Messages.MSG_NO_PERMS);
        return true;
    } else {
    PlayerData playerdata = PlayerData.getPlayerData(sender_p);
    playerdata.setCommandSpy(!playerdata.cmdspyEnabled());
    sender.sendMessage(ChatColor.GRAY + "CommandSpy " + (playerdata.cmdspyEnabled() ? "enabled" : "disabled"));
    return true;
        }
    }
}
