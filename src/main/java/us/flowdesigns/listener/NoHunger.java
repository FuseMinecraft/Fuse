package us.flowdesigns.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

import static us.flowdesigns.fuse.Fuse.plugin;

public class NoHunger implements Listener
{
    String hungerenabled = plugin.getConfig().getString("server.hunger_enabled");
    @EventHandler(priority = EventPriority.MONITOR)
    public void onFoodLevelChange(FoodLevelChangeEvent event)
    {
        if (!hungerenabled.equalsIgnoreCase("true"))
        {
        event.setFoodLevel(20);
        }
    }
}