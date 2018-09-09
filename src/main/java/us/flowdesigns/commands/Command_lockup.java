package us.flowdesigns.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import us.flowdesigns.utils.NUtil;
import us.flowdesigns.utils.PlayerData;

@CommandPermissions(source = SourceType.BOTH)
@CommandParameters(description = "Block a players Minecraft input", usage = "/<command> <all | purge | <<name> on | off>>")
public class Command_lockup extends BaseCommand
{
    @Override
    public boolean run(final CommandSender sender, final Player sender_p, final Command cmd, final String commandLabel, final String[] args, final boolean senderIsConsole)
    {
        boolean fun = plugin.getConfig().getBoolean("commands.fun_commands");
        if (!fun)
        {
            sender.sendMessage(Messages.UNKNOWN_COMMAND);
            return true;
        }
        if (!sender.hasPermission("nitrogen.lockup"))
        {
            sender.sendMessage(Messages.MSG_NO_PERMS);
            return true;
        }
        if (args.length == 1)
        {
            if (args[0].equalsIgnoreCase("all"))
            {
                NUtil.adminAction(sender.getName(), "Locking up all players", true);

                for (Player player : server.getOnlinePlayers())
                {
                    startLockup(player);
                }
                sender.sendMessage(ChatColor.GRAY + "Locked up all players.");
            }
            else if (args[0].equalsIgnoreCase("purge"))
            {
                NUtil.adminAction(sender.getName(), "Unlocking all players", true);
                for (Player player : server.getOnlinePlayers())
                {
                    cancelLockup(player);
                }

                sender.sendMessage(ChatColor.GRAY + "Unlocked all players.");
            }
            else
            {
                return false;
            }
        }
        else if (args.length == 2)
        {
            if (args[1].equalsIgnoreCase("on"))
            {
                final Player player = getPlayer(args[0]);

                if (player == null)
                {
                    sender.sendMessage(Messages.PLAYER_NOT_FOUND);
                    return true;
                }

                NUtil.adminAction(sender.getName(), "Locking up " + player.getName(), true);
                startLockup(player);
                sender.sendMessage(ChatColor.GRAY + "Locked up " + player.getName() + ".");
            }
            else if ("off".equals(args[1]))
            {
                final Player player = getPlayer(args[0]);

                if (player == null)
                {
                    sender.sendMessage(Messages.PLAYER_NOT_FOUND);
                    return true;
                }

                NUtil.adminAction(sender.getName(), "Unlocking " + player.getName(), true);
                cancelLockup(player);
                sender.sendMessage(ChatColor.GRAY + "Unlocked " + player.getName() + ".");
            }
            else
            {
                return false;
            }
        }
        else
        {
            return false;
        }

        return true;
    }

    private void cancelLockup(PlayerData playerdata)
    {
        BukkitTask lockupScheduleId = playerdata.getLockupScheduleID();
        if (lockupScheduleId != null)
        {
            lockupScheduleId.cancel();
            playerdata.setLockupScheduleId(null);
        }
    }

    private void cancelLockup(final Player player)
    {
        cancelLockup(PlayerData.getPlayerData(player));
    }

    private void startLockup(final Player player)
    {
        final PlayerData playerdata = PlayerData.getPlayerData(player);

        cancelLockup(playerdata);

        playerdata.setLockupScheduleId(new BukkitRunnable()
        {
            @Override
            public void run()
            {
                if (player.isOnline())
                {
                    player.openInventory(player.getInventory());
                }
                else
                {
                    cancelLockup(playerdata);
                }
            }
        }.runTaskTimer(plugin, 0L, 5L));
    }
}
