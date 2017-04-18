package com.fusenetworks.fuse.listener;
 
import static com.fusenetworks.fuse.Fuse.plugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

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