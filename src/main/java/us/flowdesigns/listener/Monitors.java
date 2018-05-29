package us.flowdesigns.listener;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import us.flowdesigns.fuse.Fuse;
import us.flowdesigns.utils.PlayerData;

import static us.flowdesigns.fuse.Fuse.plugin;
import static us.flowdesigns.fuse.Fuse.server;

public class Monitors implements Listener {

    // Potion Listener
    String potions_enabled = plugin.getConfig().getString("server.splash_potions_enabled");
    @EventHandler
    public void onPotionSplashEvent(final PotionSplashEvent event) {
        Player player = (Player) event.getEntity().getShooter();
        Entity entity = event.getEntity();
        switch (event.getPotion().getType()) {
            case SPLASH_POTION:
                player.getInventory().setItem(player.getInventory().getHeldItemSlot(), new ItemStack(Material.AIR, 1));
                player.sendMessage(ChatColor.RED + "Splash potions are disabled");
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
                }.runTaskLater(Fuse.getInstance(), 1);
                break;
        }
    }

    // Developer Mode
    @EventHandler
    public boolean onPlayerJoin(PlayerJoinEvent event) {
        String dev = plugin.getConfig().getString("server.dev");
        Player player = event.getPlayer();
        if (dev.equals("true")) {
            player.sendMessage(ChatColor.DARK_AQUA + "Warning: The server is currently in development mode. "
                    + "This means there may be unstable plugin builds on this server, and the server could crash more than normal!");
            return true;
        }
        return true;
    }

    // Command Blocker
    final PluginManager pm = server.getPluginManager();
    @EventHandler
    public boolean PlayerCommandPreprocessEvent(PlayerCommandPreprocessEvent event) {
        String command = event.getMessage();
        final String[] commandParts = command.split(" ");
        if (commandParts[0].contains(":"))
        {
            event.getPlayer().sendMessage(ChatColor.GRAY + "Plugin specific commands are disabled");
            event.setCancelled(true);
            return true;
        }
        return true;
    }

    // Commandspy
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
        Bukkit.getOnlinePlayers().stream().filter((pl) -> (PlayerData.getPlayerData(pl).cmdspyEnabled())).forEach((Player pl) -> {
            final Player player = event.getPlayer();
            String command = event.getMessage();
            if (pl != player) {
                pl.sendMessage(ChatColor.GRAY + player.getName() + ": " + command);
            }
        });
    }
}
