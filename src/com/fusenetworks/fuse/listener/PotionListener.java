package com.fusenetworks.fuse.listener;

import com.fusenetworks.fuse.SomeLegacyCodeMyShittyPotionListenerNeeds;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class PotionListener implements Listener {

    String potions_enabled = Bukkit.getPluginManager().getPlugin("Fuse").getConfig().getString("server.splash_potions_enabled");
    @EventHandler
    public void onPotionSplashEvent(final PotionSplashEvent event) {
        Player player = (Player) event.getEntity().getShooter();
        Entity entity = event.getEntity();

        switch (event.getPotion().getType()) {
            case SPLASH_POTION:
                player.getInventory().setItem(player.getInventory().getHeldItemSlot(), new ItemStack(Material.AIR, 1));
                player.sendMessage(ChatColor.GOLD + "Fuse >> Splash potions are disabled");
                event.setCancelled(true);
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        event.getAffectedEntities().forEach((entity) -> {
                            event.getPotion().getEffects().stream().filter((effect) -> (entity instanceof Player)).forEachOrdered((effect) -> {
                                Player player = (Player) entity;
                                player.removePotionEffect(effect.getType());
                            });
                        });
                    }
                }.runTaskLater(SomeLegacyCodeMyShittyPotionListenerNeeds.getInstance(), 1);
                break;
        }
    }
}