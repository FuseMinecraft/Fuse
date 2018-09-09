package us.flowdesigns.commands;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;

import java.util.Random;

@CommandPermissions(source = SourceType.ONLY_IN_GAME)
@CommandParameters(description = "Shoots a firework into the sky", usage = "/<command>")
public class Command_fw extends BaseCommand
{

    private final Random rnd = new Random();
    private final Color[] colors = {
            Color.AQUA,
            Color.BLACK,
            Color.BLUE,
            Color.FUCHSIA,
            Color.GREEN,
            Color.LIME,
            Color.MAROON,
            Color.OLIVE,
            Color.ORANGE,
            Color.PURPLE,
            Color.RED,
            Color.TEAL,
            Color.WHITE,
            Color.YELLOW
    };

    @Override
    public boolean run(CommandSender sender, Player sender_p, Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
    {

        if (senderIsConsole)
        {
            sender.sendMessage(Messages.PLAYER_ONLY);
            return true;
        }

        Player p = (Player)sender;
        Location loc = p.getLocation();
        if (loc != null)
        {
            World w = loc.getWorld();
            Firework fw = (Firework)w.spawnEntity(loc, EntityType.FIREWORK);
            FireworkMeta fm = fw.getFireworkMeta();
            FireworkEffect.Builder effect = FireworkEffect.builder();
            effect.flicker(this.rnd.nextInt(10) > 2);
            effect.trail(this.rnd.nextInt(10) > 2);
            FireworkEffect.Type[] types = FireworkEffect.Type.values();
            effect.with(types[this.rnd.nextInt(types.length)]);
            int colorcnt = this.rnd.nextInt(3) + 2;
            for (int i = 0; i < colorcnt; i++)
            {
                effect.withColor(this.colors[this.rnd.nextInt(this.colors.length)]);
            }
            fm.addEffect(effect.build());
            fm.setPower(1);
            fw.setFireworkMeta(fm);
            sender.sendMessage(ChatColor.GRAY + "You shot a firework into the sky!");
            return true;
        }
        if (loc == null)
        {
            sender.sendMessage(ChatColor.RED + "Invalid location");
        }
        return true;
    }
}