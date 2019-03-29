package me.telesphoreo.listener;

import me.telesphoreo.Nitrogen;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

public class ConfigListener implements Listener
{
    private Nitrogen plugin = Nitrogen.plugin;

    // Drop items on death
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e)
    {
        if (!plugin.getConfig().getBoolean("server.drop_items_on_death"))
        {
            e.getDrops().clear();
        }
    }

    // Teleport players to spawn
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e)
    {
        Player player = e.getPlayer();
        boolean teleportSpawn = plugin.getConfig().getBoolean("server.spawn_on_join");
        boolean clearInventoryOnJoin = plugin.getConfig().getBoolean("server.clear_inventory_on_join");
        if (teleportSpawn)
        {
            player.teleport(player.getWorld().getSpawnLocation());
        }
        if (clearInventoryOnJoin)
        {
            player.getPlayer().getInventory().clear();
        }
    }

    // Hunger Enabled
    private boolean hungerenabled = plugin.getConfig().getBoolean("server.hunger_enabled");

    @EventHandler(priority = EventPriority.MONITOR)
    public void onFoodLevelChange(FoodLevelChangeEvent event)
    {
        if (!hungerenabled)
        {
            event.setFoodLevel(20);
        }
    }

    // Weather enabled
    private boolean weatherenabled = plugin.getConfig().getBoolean("server.weather_enabled");

    @EventHandler
    public void onWeatherChange(WeatherChangeEvent event)
    {
        if (!weatherenabled)
        {
            event.setCancelled(true);
        }
    }

    // Fall Damage Enabled
    private boolean falldamage = plugin.getConfig().getBoolean("server.fall_damage_enabled");

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerFall(EntityDamageEvent event)
    {
        if (!falldamage)
        {
            if (event.getCause() == EntityDamageEvent.DamageCause.FALL)
            {
                event.setCancelled(true);
            }
        }
    }
}
