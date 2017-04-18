/*

Delete later

package com.fusenetworks.fuse.commands;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.inventory.meta.ItemMeta;

@CommandPermissions(source = SourceType.BOTH)
@CommandParameters(description = "Get a kit", usage = "/<command>", aliases = "kits")
public class Command_kit extends BaseCommand {

    public static String trans(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    @Override
    public boolean run(final CommandSender sender, final Player sender_p, final Command cmd, final String commandLabel, final String[] args, final boolean senderIsConsole) {
        String servername = plugin.getConfig().getString("server.location");
        String op_kits = plugin.getConfig().getString("server.op_kits");
        String owner = plugin.getConfig().getString("staff.owner");
        if (!servername.equals("kitpvp") && !servername.equals("factions") && !servername.equals("prison"))
            {
            sender.sendMessage(Messages.INVALID_SERVER);
            return true;
            }
        if (senderIsConsole)
        {
            sender.sendMessage(Messages.PLAYER_ONLY);
            return true;
        }
        if (!senderIsConsole) {
            if (servername.equalsIgnoreCase("kitpvp")) {
                if (args.length != 1) {
                    sender.sendMessage(ChatColor.GOLD + "Kits: " + ChatColor.WHITE + "Swordsman, Archer, Pyro, Ninja, Ghost\n"
                    + ChatColor.RED + "Type /kit <kitname> to get a kit");
                    return true;
                } // end args

                switch (args[0].toLowerCase()) {
                    case "archer": {
                        sender_p.getInventory().clear();
                        sender_p.removePotionEffect(PotionEffectType.SPEED);
                        sender_p.removePotionEffect(PotionEffectType.INVISIBILITY);
                        sender_p.removePotionEffect(PotionEffectType.HEALTH_BOOST);
                        //
                        ItemStack bow = new ItemStack(Material.BOW);
                        ItemStack sword = new ItemStack(Material.STONE_SWORD);
                        ItemStack helmet = new ItemStack(Material.CHAINMAIL_HELMET);
                        ItemStack boots = new ItemStack(Material.CHAINMAIL_BOOTS);
                        ItemStack leggings = new ItemStack(Material.CHAINMAIL_LEGGINGS);
                        ItemStack chestplate = new ItemStack(Material.CHAINMAIL_CHESTPLATE);
                        //
                        ItemStack arrow = new ItemStack(Material.ARROW);
                        //ItemStack shield = new ItemStack(Material.SHIELD);
                        //
                        Enchantment p = Enchantment.PROTECTION_ENVIRONMENTAL;
                        Enchantment s = Enchantment.DAMAGE_ALL;
                        Enchantment power = Enchantment.ARROW_KNOCKBACK;
                        //
                        helmet.addEnchantment(p, 1);
                        boots.addEnchantment(p, 1);
                        leggings.addEnchantment(p, 1);
                        chestplate.addEnchantment(p, 1);
                        bow.addEnchantment(power, 2);
                        bow.addEnchantment(Enchantment.ARROW_INFINITE, 1);
                        sword.addEnchantment(s, 2);
                        //
                        sender_p.getInventory().addItem(bow);
                        sender_p.getInventory().addItem(arrow);
                        sender_p.getInventory().addItem(sword);
                        sender_p.getInventory().setHelmet(helmet);
                        sender_p.getInventory().setChestplate(chestplate);
                        sender_p.getInventory().setLeggings(leggings);
                        sender_p.getInventory().setBoots(boots);
                        //sender_p.getInventory().addItem(shield);
                        //
                        sender.sendMessage(ChatColor.AQUA + "You have been given the Archer kit!");
                        return true;
                    }
                    case "ninja": {
                        sender_p.getInventory().clear();
                        sender_p.removePotionEffect(PotionEffectType.SPEED);
                        sender_p.removePotionEffect(PotionEffectType.INVISIBILITY);
                        sender_p.removePotionEffect(PotionEffectType.HEALTH_BOOST);
                        PotionEffect speed = new PotionEffect(PotionEffectType.SPEED, 99999999 * 20, 3);
                        //
                        ItemStack sword = new ItemStack(Material.IRON_SWORD);
                        ItemStack helmet = new ItemStack(Material.CHAINMAIL_HELMET);
                        ItemStack boots = new ItemStack(Material.CHAINMAIL_BOOTS);
                        ItemStack leggings = new ItemStack(Material.CHAINMAIL_LEGGINGS);
                        ItemStack chestplate = new ItemStack(Material.CHAINMAIL_CHESTPLATE);
                        //
                        ItemStack enderpearls = new ItemStack(Material.ENDER_PEARL, 64);
                        //ItemStack shield = new ItemStack(Material.SHIELD);
                        //
                        Enchantment d = Enchantment.DURABILITY;
                        Enchantment s = Enchantment.DAMAGE_ALL;
                        //
                        helmet.addEnchantment(d, 3);
                        boots.addEnchantment(d, 3);
                        leggings.addEnchantment(d, 3);
                        chestplate.addEnchantment(d, 3);
                        sword.addEnchantment(s, 1);
                        //
                        sender_p.getInventory().addItem(sword);
                        sender_p.getInventory().addItem(enderpearls);
                        //sender_p.getInventory().addItem(shield);
                        sender_p.getInventory().setHelmet(helmet);
                        sender_p.getInventory().setChestplate(chestplate);
                        sender_p.getInventory().setLeggings(leggings);
                        sender_p.getInventory().setBoots(boots);
                        sender_p.addPotionEffect(speed);
                        //
                        sender.sendMessage(ChatColor.AQUA + "You have been given the Ninja kit!");
                        break;
                    }
                    case "swordsman": {
                        sender_p.getInventory().clear();
                        sender_p.removePotionEffect(PotionEffectType.SPEED);
                        sender_p.removePotionEffect(PotionEffectType.INVISIBILITY);
                        sender_p.removePotionEffect(PotionEffectType.HEALTH_BOOST);
                        //
                        ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
                        ItemStack helmet = new ItemStack(Material.IRON_HELMET);
                        ItemStack boots = new ItemStack(Material.IRON_BOOTS);
                        ItemStack leggings = new ItemStack(Material.IRON_LEGGINGS);
                        ItemStack chestplate = new ItemStack(Material.IRON_CHESTPLATE);
                        //
                        //ItemStack shield = new ItemStack(Material.SHIELD);
                        //
                        Enchantment d = Enchantment.DURABILITY;
                        Enchantment s = Enchantment.DAMAGE_ALL;
                        //
                        helmet.addEnchantment(d, 3);
                        boots.addEnchantment(d, 3);
                        leggings.addEnchantment(d, 3);
                        chestplate.addEnchantment(d, 3);
                        sword.addEnchantment(s, 1);
                        //
                        sender_p.getInventory().addItem(sword);
                        //sender_p.getInventory().addItem(shield);
                        sender_p.getInventory().setHelmet(helmet);
                        sender_p.getInventory().setChestplate(chestplate);
                        sender_p.getInventory().setLeggings(leggings);
                        sender_p.getInventory().setBoots(boots);
                        //
                        sender.sendMessage(ChatColor.AQUA + "You have been given the Swordsman kit!");
                        break;
                    }
                    case "xshotzfired_": {
                        if (op_kits.equalsIgnoreCase("false"))
                        {
                            sender.sendMessage(ChatColor.GOLD + "" + plugin.getName() + " >> OP Kits are disabled");
                            return true;
                        }
                        if (sender.getName().equalsIgnoreCase("xShotzfired_"))
                        {
                        sender_p.getInventory().clear();
                        sender_p.removePotionEffect(PotionEffectType.SPEED);
                        sender_p.removePotionEffect(PotionEffectType.INVISIBILITY);
                        sender_p.removePotionEffect(PotionEffectType.HEALTH_BOOST);
                        //
                        ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
                        ItemStack bow = new ItemStack(Material.BOW);
                        ItemStack arrow = new ItemStack(Material.ARROW);
                        ItemStack helmet = new ItemStack(Material.IRON_HELMET);
                        ItemStack boots = new ItemStack(Material.IRON_BOOTS);
                        ItemStack leggings = new ItemStack(Material.IRON_LEGGINGS);
                        ItemStack chestplate = new ItemStack(Material.IRON_CHESTPLATE);
                        //
                        //ItemStack shield = new ItemStack(Material.SHIELD);
                        //
                        Enchantment d = Enchantment.DURABILITY;
                        Enchantment s = Enchantment.DAMAGE_ALL;
                        //
                        helmet.addEnchantment(d, 3);
                        boots.addEnchantment(d, 3);
                        leggings.addEnchantment(d, 3);
                        chestplate.addEnchantment(d, 3);
                        sword.addEnchantment(s, 5);
                        bow.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 1337);
                        bow.addUnsafeEnchantment(Enchantment.ARROW_KNOCKBACK, 1337);
                        bow.addUnsafeEnchantment(Enchantment.ARROW_FIRE, 1337);
                        bow.addUnsafeEnchantment(Enchantment.ARROW_INFINITE, 1337);
                        //
                        sender_p.getInventory().addItem(sword);
                        sender_p.getInventory().addItem(bow);
                        sender_p.getInventory().addItem(arrow);
                        //sender_p.getInventory().addItem(shield);
                        sender_p.getInventory().setHelmet(helmet);
                        sender_p.getInventory().setChestplate(chestplate);
                        sender_p.getInventory().setLeggings(leggings);
                        sender_p.getInventory().setBoots(boots);
                        //
                        sender.sendMessage(ChatColor.AQUA + "You have been given the xShotzfired_ kit!");
                        return true;
                        } else {
                            sender.sendMessage(Messages.INVALID_KIT);
                        }
                        break;
                    }
                    case "isthisnigga4real": {
                        if (op_kits.equalsIgnoreCase("false"))
                        {
                            sender.sendMessage(ChatColor.GOLD + "" + plugin.getName() + " >> OP Kits are disabled");
                            return true;
                        }
                        if (sender.getName().equals("superkai69")
                                || sender.getName().equals("CountCringe")
                                || sender.getName().equals("Ninjacrafter359")
                                || sender.getName().equals("OxLemonxO")
                                || sender.getName().equals("xShotzfired_"))
                        {
                            sender_p.getInventory().clear();
                            sender_p.removePotionEffect(PotionEffectType.SPEED);
                            sender_p.removePotionEffect(PotionEffectType.INVISIBILITY);
                            sender_p.removePotionEffect(PotionEffectType.HEALTH_BOOST);
                            //
                            ItemStack bow = new ItemStack(Material.BOW);
                            ItemStack arrow = new ItemStack(Material.ARROW);
                            //
                            bow.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 1337);
                            bow.addUnsafeEnchantment(Enchantment.ARROW_KNOCKBACK, 1337);
                            bow.addUnsafeEnchantment(Enchantment.ARROW_FIRE, 1337);
                            bow.addUnsafeEnchantment(Enchantment.ARROW_INFINITE, 1337);
                            //
                            sender_p.getInventory().addItem(bow);
                            sender_p.getInventory().addItem(arrow);
                            //
                            sender.sendMessage(ChatColor.AQUA + "You have been given the secret isthisnigga4real kit!");
                        } else {
                            sender.sendMessage(Messages.INVALID_KIT);
                        }
                        break;
                    }
                    case "pyro": {
                        sender_p.getInventory().clear();
                        sender_p.removePotionEffect(PotionEffectType.SPEED);
                        sender_p.removePotionEffect(PotionEffectType.INVISIBILITY);
                        sender_p.removePotionEffect(PotionEffectType.HEALTH_BOOST);
                        //
                        ItemStack sword = new ItemStack(Material.IRON_SWORD);
                        ItemStack bow = new ItemStack(Material.BOW);
                        ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
                        ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
                        ItemStack leggings = new ItemStack(Material.LEATHER_LEGGINGS);
                        ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
                        //
                        ItemStack arrow = new ItemStack(Material.ARROW);
                        //ItemStack shield = new ItemStack(Material.SHIELD);
                        //
                        Enchantment d = Enchantment.DURABILITY;
                        Enchantment fire = Enchantment.FIRE_ASPECT;
                        Enchantment p = Enchantment.PROTECTION_ENVIRONMENTAL;
                        //
                        helmet.addEnchantment(p, 1);
                        boots.addEnchantment(p, 1);
                        leggings.addEnchantment(p, 1);
                        chestplate.addEnchantment(p, 1);
                        helmet.addEnchantment(d, 3);
                        boots.addEnchantment(d, 3);
                        leggings.addEnchantment(d, 3);
                        chestplate.addEnchantment(d, 3);
                        sword.addEnchantment(fire, 2);
                        bow.addEnchantment(Enchantment.ARROW_INFINITE, 1);
                        bow.addEnchantment(Enchantment.ARROW_FIRE, 1);
                        //
                        sender_p.getInventory().addItem(sword);
                        sender_p.getInventory().addItem(bow);
                        sender_p.getInventory().addItem(arrow);
                        //sender_p.getInventory().addItem(shield);
                        sender_p.getInventory().setHelmet(helmet);
                        sender_p.getInventory().setChestplate(chestplate);
                        sender_p.getInventory().setLeggings(leggings);
                        sender_p.getInventory().setBoots(boots);
                        //
                        sender.sendMessage(ChatColor.AQUA + "You have been given the Pyro kit!");
                        break;
                    }
                    case "oxlemonxo": {
                        if (op_kits.equalsIgnoreCase("false"))
                        {
                            sender.sendMessage(ChatColor.GOLD + "" + plugin.getName() + " >> OP Kits are disabled");
                            return true;
                        }
                        if (!sender.getName().equalsIgnoreCase("OxLemonxO")) {
                            sender.sendMessage(Messages.INVALID_KIT);
                            return true;
                        } else {
                            sender_p.getInventory().clear();
                            sender_p.removePotionEffect(PotionEffectType.SPEED);
                            sender_p.removePotionEffect(PotionEffectType.INVISIBILITY);
                            sender_p.removePotionEffect(PotionEffectType.HEALTH_BOOST);
                            PotionEffect speed = new PotionEffect(PotionEffectType.SPEED, 99999999 * 20, 3);
                            //
                            ItemStack lemon = new ItemStack(Material.GOLD_SWORD);
                            ItemStack helmet = new ItemStack(Material.DIAMOND_HELMET);
                            ItemStack boots = new ItemStack(Material.DIAMOND_BOOTS);
                            ItemStack leggings = new ItemStack(Material.DIAMOND_LEGGINGS);
                            ItemStack chestplate = new ItemStack(Material.DIAMOND_CHESTPLATE);
                            //
                            ItemStack enderpearls = new ItemStack(Material.ENDER_PEARL, 64);                           
                            //
                            ItemMeta meta = lemon.getItemMeta();
                            List<String> lore = new ArrayList<>();
                            String name = trans("&CL&Ee&Am&Bo&9n&D &CS&Ew&Ao&Br&9d");
                            lore.add(trans("&CL&Ee&Am&Bo&9n&Ds&C &Et&Aa&Bs&9t&De&C &Eg&Ao&Bo&9d&D.&C.&E."));
                            meta.setLore(lore);
                            meta.setDisplayName(name);
                            meta.spigot().setUnbreakable(true);
                            lemon.setItemMeta(meta);
                            //
                            ItemMeta meta2 = helmet.getItemMeta();
                            meta2.setLore(lore);
                            meta2.setDisplayName(trans("&CL&Ee&Am&Bo&9n&Da&Cd&Ee&A &BH&9e&Dl&Cm&Ee&At"));
                            meta2.spigot().setUnbreakable(true);
                            helmet.setItemMeta(meta2);
                            //
                            ItemMeta meta3 = chestplate.getItemMeta();
                            meta3.setLore(lore);
                            meta3.setDisplayName(trans("&CL&Ere&Am&Bo&9n&Da&Cd&Ee&A &BC&9h&De&Cs&Et&Ap&Bl&9a&Dt&Ce"));
                            meta3.spigot().setUnbreakable(true);
                            chestplate.setItemMeta(meta3);
                            //
                            ItemMeta meta4 = leggings.getItemMeta();
                            meta4.setLore(lore);
                            meta4.setDisplayName(trans("&CL&Ee&Am&Bo&9n&Da&Cd&Ee&A &BL&9e&Dg&Cg&Ei&An&Bg&9s"));
                            meta4.spigot().setUnbreakable(true);
                            leggings.setItemMeta(meta4);
                            //
                            ItemMeta meta5 = boots.getItemMeta();
                            meta5.setLore(lore);
                            meta5.setDisplayName(trans("&CL&Ee&Am&Bo&9n&Da&Cd&Ee&A &BB&9o&Do&Ct&Es"));
                            meta5.spigot().setUnbreakable(true);
                            boots.setItemMeta(meta5);
                            //
                            ItemMeta meta6 = enderpearls.getItemMeta();
                            meta6.setLore(lore);
                            meta6.setDisplayName(trans("&CL&Ee&Am&Bo&9n&D &CP&Ee&Aa&Br&9l&Ds"));
                            enderpearls.setItemMeta(meta);
                            //
                            for(Enchantment ench : Enchantment.values()) {
                                int level = 32767;
                                lemon.addUnsafeEnchantment(ench, level);
                                boots.addUnsafeEnchantment(ench, level);
                                chestplate.addUnsafeEnchantment(ench, level);
                                leggings.addUnsafeEnchantment(ench, level);
                                helmet.addUnsafeEnchantment(ench, level);
                            }
                            //
                            sender_p.getInventory().addItem(lemon);
                            sender_p.getInventory().addItem(enderpearls);
                            sender_p.getInventory().setHelmet(helmet);
                            sender_p.getInventory().setChestplate(chestplate);
                            sender_p.getInventory().setLeggings(leggings);
                            sender_p.getInventory().setBoots(boots);
                            sender_p.addPotionEffect(speed);
                            sender_p.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, 99999999, 3));
                            //
                            sender.sendMessage(ChatColor.AQUA + "You have been given the Amazing Lemon kit!");

                        }
                        break;
                    }

                    case "superkai69": {
                        if (op_kits.equalsIgnoreCase("false"))
                        {
                            sender.sendMessage(ChatColor.GOLD + "" + plugin.getName() + " >> OP Kits are disabled");
                            return true;
                        }
                    if (sender.getName().equals(owner)) {
                        sender_p.getInventory().clear();
                        sender_p.removePotionEffect(PotionEffectType.SPEED);
                        sender_p.removePotionEffect(PotionEffectType.INVISIBILITY);
                        sender_p.removePotionEffect(PotionEffectType.HEALTH_BOOST);
                        sender_p.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, 999999999, 3));
                        //
                        ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
                        ItemStack bow = new ItemStack(Material.BOW);
                        ItemStack arrow = new ItemStack(Material.ARROW);
                        ItemStack helmet = new ItemStack(Material.DIAMOND_HELMET);
                        ItemStack boots = new ItemStack(Material.DIAMOND_BOOTS);
                        ItemStack leggings = new ItemStack(Material.DIAMOND_LEGGINGS);
                        ItemStack chestplate = new ItemStack(Material.DIAMOND_CHESTPLATE);
                        //ItemStack shield = new ItemStack(Material.SHIELD);
                        //
                        bow.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 32767);
                        bow.addUnsafeEnchantment(Enchantment.ARROW_KNOCKBACK, 32767);
                        bow.addUnsafeEnchantment(Enchantment.ARROW_FIRE, 32767);
                        bow.addUnsafeEnchantment(Enchantment.ARROW_INFINITE, 32767);
                        sword.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 32767);
                        sword.addUnsafeEnchantment(Enchantment.DAMAGE_ARTHROPODS, 32767);
                        sword.addUnsafeEnchantment(Enchantment.KNOCKBACK, 32767);
                        sword.addUnsafeEnchantment(Enchantment.FIRE_ASPECT, 32767);
                        sword.addUnsafeEnchantment(Enchantment.DURABILITY, 32767);
                        helmet.addUnsafeEnchantment(Enchantment.OXYGEN, 32767);
                        helmet.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 32767);
                        helmet.addUnsafeEnchantment(Enchantment.PROTECTION_EXPLOSIONS, 32767);
                        helmet.addUnsafeEnchantment(Enchantment.PROTECTION_FALL, 32767);
                        helmet.addUnsafeEnchantment(Enchantment.PROTECTION_FIRE, 32767);
                        helmet.addUnsafeEnchantment(Enchantment.PROTECTION_PROJECTILE, 32767);
                        helmet.addUnsafeEnchantment(Enchantment.THORNS, 32767);
                        helmet.addUnsafeEnchantment(Enchantment.DURABILITY, 32767);
                        boots.addUnsafeEnchantment(Enchantment.OXYGEN, 32767);
                        boots.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 32767);
                        boots.addUnsafeEnchantment(Enchantment.PROTECTION_EXPLOSIONS, 32767);
                        boots.addUnsafeEnchantment(Enchantment.PROTECTION_FALL, 32767);
                        boots.addUnsafeEnchantment(Enchantment.PROTECTION_FIRE, 32767);
                        boots.addUnsafeEnchantment(Enchantment.PROTECTION_PROJECTILE, 32767);
                        boots.addUnsafeEnchantment(Enchantment.THORNS, 32767);
                        boots.addUnsafeEnchantment(Enchantment.DURABILITY, 32767);
                        leggings.addUnsafeEnchantment(Enchantment.OXYGEN, 32767);
                        leggings.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 32767);
                        leggings.addUnsafeEnchantment(Enchantment.PROTECTION_EXPLOSIONS, 32767);
                        leggings.addUnsafeEnchantment(Enchantment.PROTECTION_FALL, 32767);
                        leggings.addUnsafeEnchantment(Enchantment.PROTECTION_FIRE, 32767);
                        leggings.addUnsafeEnchantment(Enchantment.PROTECTION_PROJECTILE, 32767);
                        leggings.addUnsafeEnchantment(Enchantment.THORNS, 32767);
                        leggings.addUnsafeEnchantment(Enchantment.DURABILITY, 32767);
                        chestplate.addUnsafeEnchantment(Enchantment.OXYGEN, 32767);
                        chestplate.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 32767);
                        chestplate.addUnsafeEnchantment(Enchantment.PROTECTION_EXPLOSIONS, 32767);
                        chestplate.addUnsafeEnchantment(Enchantment.PROTECTION_FALL, 32767);
                        chestplate.addUnsafeEnchantment(Enchantment.PROTECTION_FIRE, 32767);
                        chestplate.addUnsafeEnchantment(Enchantment.PROTECTION_PROJECTILE, 32767);
                        chestplate.addUnsafeEnchantment(Enchantment.THORNS, 32767);
                        chestplate.addUnsafeEnchantment(Enchantment.DURABILITY, 32767);
                        //shield.addUnsafeEnchantment(Enchantment.DURABILITY, 32767);
                        sender_p.getInventory().addItem(sword);
                        sender_p.getInventory().addItem(bow);
                        sender_p.getInventory().addItem(arrow);
                        sender_p.getInventory().setHelmet(helmet);
                        sender_p.getInventory().setChestplate(chestplate);
                        sender_p.getInventory().setLeggings(leggings);
                        sender_p.getInventory().setBoots(boots);
                        //sender_p.getInventory().addItem(shield);
                        sender.sendMessage(ChatColor.AQUA + "You have been given the superkai69 kit!");
                        return true;
                        } else {
                        sender.sendMessage(Messages.INVALID_KIT);
                    }
                        break;
                    }
                    case "ghost": {
                        sender_p.getInventory().clear();
                        sender_p.removePotionEffect(PotionEffectType.SPEED);
                        sender_p.removePotionEffect(PotionEffectType.INVISIBILITY);
                        sender_p.removePotionEffect(PotionEffectType.HEALTH_BOOST);
                        PotionEffect invisibility = new PotionEffect(PotionEffectType.INVISIBILITY, 99999999 * 20, 3);
                        ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
                        Enchantment d = Enchantment.DURABILITY;
                        Enchantment s = Enchantment.DAMAGE_ALL;
                        sword.addEnchantment(s, 3);
                        sword.addEnchantment(d, 3);
                        sender_p.getInventory().addItem(sword);
                        sender_p.addPotionEffect(invisibility);
                        sender.sendMessage(ChatColor.AQUA + "You have been given the Ghost kit!");
                        break;
                    }
                    default:
                    sender.sendMessage(Messages.INVALID_KIT);
                    break;
                }
            }
            if (servername.equals("factions")) {
                if (args.length == 0) {
                    sender.sendMessage(ChatColor.GOLD + "Kits: " + ChatColor.WHITE + "Tools\n"
                    + ChatColor.RED + "Type /kit <kitname> to get a kit");
                    return true;
                }
                switch (args[0].toLowerCase()) {
                    case "tools": {
                        ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
                        ItemStack shovel = new ItemStack(Material.DIAMOND_SPADE);
                        ItemStack pickaxe = new ItemStack(Material.DIAMOND_PICKAXE);
                        ItemStack axe = new ItemStack(Material.DIAMOND_AXE);
                        ItemStack helmet = new ItemStack(Material.DIAMOND_HELMET);
                        ItemStack boots = new ItemStack(Material.DIAMOND_BOOTS);
                        ItemStack leggings = new ItemStack(Material.DIAMOND_LEGGINGS);
                        ItemStack chestplate = new ItemStack(Material.DIAMOND_CHESTPLATE);
                        ItemStack obsidian = new ItemStack(Material.OBSIDIAN, 128);
                        ItemStack steak = new ItemStack(Material.COOKED_BEEF, 128);
                        ItemStack gapple = new ItemStack(Material.GOLDEN_APPLE, 16);
                        sword.addEnchantment(Enchantment.DURABILITY, 3);
                        sword.addEnchantment(Enchantment.DAMAGE_ALL, 2);
                        sword.addEnchantment(Enchantment.FIRE_ASPECT, 2);
                        shovel.addEnchantment(Enchantment.DURABILITY, 2);
                        shovel.addEnchantment(Enchantment.LOOT_BONUS_BLOCKS, 1);
                        shovel.addEnchantment(Enchantment.DIG_SPEED, 2);
                        pickaxe.addEnchantment(Enchantment.DURABILITY, 3);
                        pickaxe.addEnchantment(Enchantment.LOOT_BONUS_BLOCKS, 2);
                        pickaxe.addEnchantment(Enchantment.DIG_SPEED, 3);
                        axe.addEnchantment(Enchantment.DURABILITY, 2);
                        axe.addEnchantment(Enchantment.DIG_SPEED, 1);
                        helmet.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
                        helmet.addEnchantment(Enchantment.DURABILITY, 3);
                        chestplate.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
                        chestplate.addEnchantment(Enchantment.DURABILITY, 3);
                        leggings.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
                        leggings.addEnchantment(Enchantment.DURABILITY, 3);
                        boots.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
                        boots.addEnchantment(Enchantment.DURABILITY, 3);
                        sender_p.getInventory().addItem(sword);
                        sender_p.getInventory().addItem(shovel);
                        sender_p.getInventory().addItem(pickaxe);
                        sender_p.getInventory().addItem(axe);
                        sender_p.getInventory().addItem(steak);
                        sender_p.getInventory().addItem(obsidian);
                        sender_p.getInventory().addItem(gapple);
                        sender_p.getInventory().setHelmet(helmet);
                        sender_p.getInventory().setChestplate(chestplate);
                        sender_p.getInventory().setLeggings(leggings);
                        sender_p.getInventory().setBoots(boots);
                        sender.sendMessage(ChatColor.AQUA + "You have been given the tools kit");
                        break;
                    }
                    default: {
                        sender.sendMessage(Messages.INVALID_KIT);
                        return true;
                    }
                } // end args for factions
        return true;
        } // end factions
        } // end !senderIsConsole
        /*if (servername.equals("prison")) {
            if (args.length == 0) {
                sender.sendMessage(ChatColor.GOLD + "Kits: " + ChatColor.WHITE + "Starter\n"
                + ChatColor.RED + "Type /kit <kitname> to get a kit");
                return true;
            }
            switch (args[0].toLowerCase()) {
                case "starter": {
                        ItemStack pickaxe = new ItemStack(Material.DIAMOND_PICKAXE);
                        ItemStack gapple = new ItemStack(Material.GOLDEN_APPLE, 64);
                        pickaxe.addUnsafeEnchantment(Enchantment.DURABILITY, 32767);
                        pickaxe.addEnchantment(Enchantment.LOOT_BONUS_BLOCKS, 3);
                        pickaxe.addUnsafeEnchantment(Enchantment.DIG_SPEED, 32767);
                        sender_p.getInventory().addItem(pickaxe);
                        sender_p.getInventory().addItem(gapple);
                        sender.sendMessage(ChatColor.AQUA + "You have been given the starter kit");
                        break;
                    }
                    default: {
                        sender.sendMessage(Messages.INVALID_KIT);
                        return true;
                    }
                } */
        /*return true;
        } // end factions
} // end !senderIsConsole*/