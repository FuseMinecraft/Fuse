package us.flowdesigns.commands;

import us.flowdesigns.utils.NUtil;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

@CommandPermissions(source = SourceType.BOTH)
@CommandParameters(description = "Pushes players away from you", usage = "/<command>")
public class Command_expel extends BaseCommand {
    @Override
    public boolean run(final CommandSender sender, final Player sender_p, final Command cmd, final String commandLabel, final String[] args, final boolean senderIsConsole)
    {
        if (!sender.hasPermission("fuse.expel"))
        {
            sender.sendMessage(Messages.MSG_NO_PERMS);
            return true;
        }
        if (senderIsConsole)
        {
            sender.sendMessage(Messages.PLAYER_ONLY);
            return true;
        }
        double radius = 20.0;
        double strength = 5.0;

        if (args.length >= 1)
        {
            try
            {
                radius = Math.max(1.0, Math.min(100.0, Double.parseDouble(args[0])));
            }
            catch (NumberFormatException ex)
            {
            }
        }

        if (args.length >= 2)
        {
            try
            {
                strength = Math.max(0.0, Math.min(50.0, Double.parseDouble(args[1])));
            }
            catch (NumberFormatException ex)
            {
            }
        }

        List<String> pushedPlayers = new ArrayList<>();

        final Vector senderPos = sender_p.getLocation().toVector();
        final List<Player> players = sender_p.getWorld().getPlayers();
        for (final Player player : players)
        {
            if (player.equals(sender_p))
            {
                continue;
            }

            final Location targetPos = player.getLocation();
            final Vector targetPosVec = targetPos.toVector();

            boolean inRange = false;
            try
            {
                inRange = targetPosVec.distanceSquared(senderPos) < (radius * radius);
            }
            catch (IllegalArgumentException ex)
            {
            }

            if (inRange)
            {
                player.getWorld().createExplosion(targetPos, 0.0f, false);
                NUtil.setFlying(player, false);
                player.setVelocity(targetPosVec.subtract(senderPos).normalize().multiply(strength));
                pushedPlayers.add(player.getName());
            }
        }

        if (pushedPlayers.isEmpty())
        {
            sender.sendMessage(ChatColor.GRAY + "No players pushed.");
        }
        else if (pushedPlayers.size() == 1)
        {
            sender.sendMessage(ChatColor.GRAY + "Pushed " + pushedPlayers.size() + " player: " + StringUtils.join(pushedPlayers, ""));
        } else {
            sender.sendMessage(ChatColor.GRAY + "Pushed " + pushedPlayers.size() + " players: " + StringUtils.join(pushedPlayers, ", "));
        }

        return true;
    }
}
