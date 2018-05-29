package us.flowdesigns.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.*;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class NUtil
{
    public static String DATE_STORAGE_FORMAT = "EEE, MMM d, yyyy HH:mm:ss";
    public static void bcastMsg(String message, ChatColor color)
    {
        NLog.info(message, true);
        Bukkit.getOnlinePlayers().stream().forEach((player) -> {
            player.sendMessage((color == null ? "" : color) + message);
        });
    }
    public static void bcastMsg(String message)
    {
        NUtil.bcastMsg(message, null);
    }
    public static void adminAction(String adminName, String action, boolean isRed)
    {
        NUtil.bcastMsg(adminName + " - " + action, (isRed ? ChatColor.RED : ChatColor.BLUE));
    }
    public static void playerMsg(CommandSender sender, String message, ChatColor color)
    {
        sender.sendMessage(color + message);
    }
    public static void playerMsg(CommandSender sender, String message)
    {
        NUtil.playerMsg(sender, message, ChatColor.GRAY);
    }
    public static void playerAdminChat(String name, String message) {
        Bukkit.getOnlinePlayers().stream().filter((player) -> (player.hasPermission("fuse.adminchat"))).forEach((player) -> {
            player.sendMessage("§7[AdminChat] " + ChatColor.BLUE + name + "§f: " + ChatColor.AQUA + message);
        });
    }
    public static void consoleAdminChat(String name, String message) {
        Bukkit.getOnlinePlayers().stream().filter((player) -> (player.hasPermission("fuse.adminchat"))).forEach((player) -> {
            player.sendMessage("§7[AdminChat] " + ChatColor.BLUE + name + " §8[§5Console§8]"+ "§f: " + ChatColor.AQUA + message);
        });
    }
    public static String dateToString(Date date)
    {
        return new SimpleDateFormat(DATE_STORAGE_FORMAT, Locale.ENGLISH).format(date);
    }
    public static Date stringToDate(String dateString)
    {
        try
        {
            return new SimpleDateFormat(DATE_STORAGE_FORMAT, Locale.ENGLISH).parse(dateString);
        }
        catch (ParseException pex)
        {
            return new Date(0L);
        }
    }

    public static String getNmsVersion()
    {
        String packageName = Bukkit.getServer().getClass().getPackage().getName();
        return packageName.substring(packageName.lastIndexOf('.') + 1);
    }

    public static String colorize(String string)
    {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public static void setFlying(Player player, boolean flying)
    {
        if (player.getAllowFlight() == true)
        {
            player.setAllowFlight(true);
            player.setFlying(flying);
        } else {
            player.setAllowFlight(true);
            player.setFlying(flying);
            player.setAllowFlight(false);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T getField(Object from, String name)
    {
        Class<?> checkClass = from.getClass();
        do
        {
            try
            {
                Field field = checkClass.getDeclaredField(name);
                field.setAccessible(true);
                return (T) field.get(from);
            }
            catch (NoSuchFieldException | IllegalAccessException ex)
            {
            }
        }
        while (checkClass.getSuperclass() != Object.class
                && ((checkClass = checkClass.getSuperclass()) != null));
        return null;
    }
    public static class NEntityWiper
    {
        private static final List<Class<? extends Entity>> WIPEABLES = new ArrayList<>();

        static
        {
            WIPEABLES.add(EnderCrystal.class);
            WIPEABLES.add(EnderSignal.class);
            WIPEABLES.add(ExperienceOrb.class);
            WIPEABLES.add(Projectile.class);
            WIPEABLES.add(FallingBlock.class);
            WIPEABLES.add(Firework.class);
            WIPEABLES.add(Item.class);
        }
        private NEntityWiper()
        {
            throw new AssertionError();
        }
        private static boolean canWipe(Entity entity, boolean wipeExplosives, boolean wipeVehicles)
        {
            if (wipeExplosives)
            {
                if (Explosive.class.isAssignableFrom(entity.getClass()))
                {
                    return true;
                }
            }
            if (wipeVehicles)
            {
                if (Boat.class.isAssignableFrom(entity.getClass()))
                {
                    return true;
                }
                else if (Minecart.class.isAssignableFrom(entity.getClass()))
                {
                    return true;
                }
            }
            Iterator<Class<? extends Entity>> it = WIPEABLES.iterator();
            while (it.hasNext())
            {
                if (it.next().isAssignableFrom(entity.getClass()))
                {
                    return true;
                }
            }
            return false;
        }
        public static int wipeEntities(boolean wipeExplosives, boolean wipeVehicles)
        {
            int removed = 0;
            Iterator<World> worlds = Bukkit.getWorlds().iterator();
            while (worlds.hasNext())
            {
                Iterator<Entity> entities = worlds.next().getEntities().iterator();
                while (entities.hasNext())
                {
                    Entity entity = entities.next();
                    if (canWipe(entity, wipeExplosives, wipeVehicles))
                    {
                        entity.remove();
                        removed++;
                    }
                }
            }
            return removed;
        }
    }
    public static String getNMSVersion()
    {
        String packageName = Bukkit.getServer().getClass().getPackage().getName();
        return packageName.substring(packageName.lastIndexOf('.') + 1);
    }
}