package com.fusenetworks.fuse.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

@CommandPermissions(source = SourceType.BOTH)
@CommandParameters(description = "Makes yourself invisible and glow", usage = "/<command> <enable | disable>")
public class Command_glassmode extends BaseCommand {
    @Override
    public boolean run(final CommandSender sender, final Player sender_p, final Command cmd, final String commandLabel, final String[] args, final boolean senderIsConsole) 
    {
        if (!sender.hasPermission("fuse.glassmode"))
        {
            sender.sendMessage(Messages.MSG_NO_PERMS);
            return true;
        }
        if (senderIsConsole)
        {
            sender.sendMessage(Messages.PLAYER_ONLY);
            return true;
        }
        if (args.length != 1)
        {
            return false;
        }
        if (args[0].equals("enable") || args[0].equals("on"))
        {
        PotionEffect invisible = new PotionEffect(PotionEffectType.INVISIBILITY, 99999999 * 20, 3);
        PotionEffect glow = new PotionEffect(PotionEffectType.GLOWING, 99999999 * 20, 3);
        sender_p.addPotionEffect(invisible);
        sender_p.addPotionEffect(glow);
        sender.sendMessage(ChatColor.BLUE + "Enabled glass mode");
        return true;
        }
        if (args[0].equals("disable") || args[0].equals("off"))
        {
        sender_p.removePotionEffect(PotionEffectType.INVISIBILITY);
        sender_p.removePotionEffect(PotionEffectType.GLOWING);
        sender.sendMessage(ChatColor.BLUE + "Disabled glass mode");
        return true;
        } else {
            return false;
        }
    }
}
