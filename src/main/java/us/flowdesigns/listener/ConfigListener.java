package us.flowdesigns.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

import static us.flowdesigns.nitrogen.Nitrogen.plugin;

public class ConfigListener implements Listener {

    // Drop items on death
   @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        if (plugin.getConfig().getString("server.drop_items_on_death").equalsIgnoreCase("false"))
        {
        e.getDrops().clear();
        }
    }

    // Teleport players to spawn
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e)
    {
    Player player = e.getPlayer();
    String teleportSpawn = plugin.getConfig().getString("server.spawn_on_join");
    String clearInventoryOnJoin = plugin.getConfig().getString("server.clear_inventory_on_join");
    if ("true".equals(teleportSpawn)) {
        player.teleport(player.getWorld().getSpawnLocation());
        }
        if ("true".equals(clearInventoryOnJoin)) {
        player.getPlayer().getInventory().clear();
        }
    }

    // Hunger Enabled
    String hungerenabled = plugin.getConfig().getString("server.hunger_enabled");
    @EventHandler(priority = EventPriority.MONITOR)
    public void onFoodLevelChange(FoodLevelChangeEvent event)
    {
        if (hungerenabled.equalsIgnoreCase("false"))
        {
            event.setFoodLevel(20);
        }
    }

    // Weather enabled
    String weatherenabled = plugin.getConfig().getString("server.weather_enabled");
    @EventHandler
    public void onWeatherChange(WeatherChangeEvent event)
    {
        if (weatherenabled.equalsIgnoreCase("false"))
        {
            event.setCancelled(true);
        }
    }

    // Fall Damage Enabled
    String falldamage = plugin.getConfig().getString("server.fall_damage_enabled");
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerFall(EntityDamageEvent event)
    {
        if (falldamage.equalsIgnoreCase("false"))
        {
            if (event.getCause() == EntityDamageEvent.DamageCause.FALL)
            {
                event.setCancelled(true);
            }
        }
    }
}
