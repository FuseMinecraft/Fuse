package us.flowdesigns.listener;

import org.bukkit.Bukkit;
import org.bukkit.configuration.MemorySection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import us.flowdesigns.utils.NLog;
import us.flowdesigns.utils.NUtil;

import java.util.Map;
import java.util.UUID;

import static us.flowdesigns.fuse.Fuse.plugin;

public class LoginMessages implements Listener
{
    @EventHandler
    public boolean onPlayerJoin(PlayerJoinEvent event)
    {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        String owner = plugin.getConfig().getString("players.owner");
        String login_messages_enabled = plugin.getConfig().getString("server.login_messages_enabled");
        // Telesphoreo
        /*if (uuid.toString().equals("78408086-1991-4c33-a571-d8fa325465b2") && !player.getName().equals(owner)) {
            Bukkit.broadcastMessage("§b" + player.getName() + " is a Developer for Fuse");
            return true;
        }*/
        // OxLemonxO
        if (uuid.toString().equals("e628c2b0-0e19-41d9-bb9e-af604fcb159a"))
        {
            Bukkit.broadcastMessage("§b" + player.getName() + " is a Developer for Fuse and the §4Ki§5ng §6Of §5Le§6mo§7ns§b!");
            return true;
        }
        if (login_messages_enabled.equalsIgnoreCase("true"))
        {
            try
            {
                Map<String, Object> login_messages = plugin.getConfig().getConfigurationSection("login-messages").getValues(false);
                for (String key : login_messages.keySet())
                {
                    MemorySection h = (MemorySection) login_messages.get(key);
                    String permission = (String) h.get("permission");
                    String message = (String) h.get("message");
                    if (player.hasPermission(permission))
                    {
                        Bukkit.broadcastMessage(NUtil.colorize(message.replace("%player%", player.getName())));
                    }
                }
            }
            catch (ClassCastException ex)
            {
                NLog.severe("Failed to load login messages.");
                NLog.severe(ex);
            }
        }
        return true;
    }
}
