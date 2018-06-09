package us.flowdesigns.commands;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

@CommandPermissions(source = SourceType.BOTH)
@CommandParameters(description = "Make some noise", usage = "/<command>")
public class Command_deafen extends BaseCommand {
    private static final Random random = new Random();
    public static final double STEPS = 10.0;
    @Override
    public boolean run(final CommandSender sender, final Player sender_p, final Command cmd, final String commandLabel, final String[] args, final boolean senderIsConsole) {
        {
            String fun = plugin.getConfig().getString("commands.fun_commands");
            if (!fun.equalsIgnoreCase("enabled"))
            {
                sender.sendMessage(Messages.UNKNOWN_COMMAND);
                return true;
            }
            if (!sender.hasPermission("nitrogen.deafen"))
            {
                sender.sendMessage(Messages.MSG_NO_PERMS);
                return true;
            }
            for (final Player player : server.getOnlinePlayers()) {
                for (double percent = 0.0; percent <= 1.0; percent += (1.0 / STEPS)) {
                    final float pitch = (float) (percent * 2.0);
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            player.playSound(randomOffset(player.getLocation(), 5.0), Sound.values()[random.nextInt(Sound.values().length)], 100.0f, pitch);
                        }
                    }.runTaskLater(plugin, Math.round(20.0 * percent * 2.0));
                }
            }
            return true;
        }
    }
    private static Location randomOffset(Location a, double magnitude)
    {
        return a.clone().add(randomDoubleRange(-1.0, 1.0) * magnitude, randomDoubleRange(-1.0, 1.0) * magnitude, randomDoubleRange(-1.0, 1.0) * magnitude);
    }

    private static Double randomDoubleRange(double min, double max)
    {
        return min + (random.nextDouble() * ((max - min) + 1.0));
    }
}
