package com.fusenetworks.fuse.commands;

import com.fusenetworks.fuse.util.NUtil;
import org.bukkit.Material;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

@CommandPermissions(source = SourceType.BOTH)
@CommandParameters(description = "Renames the item in your hand", usage = "/<command> <name>")
public class Command_rename extends BaseCommand {
    @Override
    public boolean run(final CommandSender sender, final Player sender_p, final Command cmd, final String commandLabel, final String[] args, final boolean senderIsConsole)
    {
        if (senderIsConsole) {
            sender.sendMessage(Messages.PLAYER_ONLY);
            return true;
        }
        if (args.length < 1)
        {
            return false;
        }
        String itemRaw = StringUtils.join(args, " ");
        String itemName = NUtil.colorize(itemRaw);
        ItemStack i = sender_p.getItemInHand();
        if (sender_p.getItemInHand().getType() == Material.AIR)
        {
            sender.sendMessage(ChatColor.RED + "You cannot rename this item");
            return true;
        } else {
            ItemMeta im = i.getItemMeta();
            im.setDisplayName(StringUtils.replace(itemName, "_", " "));
            i.setItemMeta(im);
            sender.sendMessage(ChatColor.GRAY + "Item renamed to: " + itemRaw);
        }
    return true;
    }
}