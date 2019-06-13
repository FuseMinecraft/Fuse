package me.telesphoreo;

import java.io.File;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;
import me.telesphoreo.commands.CMD_Handler;
import me.telesphoreo.commands.CMD_Loader;
import me.telesphoreo.exploits.Potion;
import me.telesphoreo.exploits.Sign;
import me.telesphoreo.listener.ConfigListener;
import me.telesphoreo.listener.Monitors;
import me.telesphoreo.utils.Config;
import me.telesphoreo.utils.NLog;
import me.telesphoreo.utils.NUtil;
import me.telesphoreo.utils.Updater;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class Nitrogen extends JavaPlugin
{
    public static Nitrogen plugin;
    public static Server server;
    public static Nitrogen instance;

    public static final BuildProperties build = new BuildProperties();
    public static final String COMPILE_NMS_VERSION = "v1_14_R1";
    public static String pluginName;
    public static String pluginVersion;

    File jarFile = this.getFile();

    @Override
    public void onLoad()
    {
        Nitrogen.plugin = this;
        Nitrogen.server = plugin.getServer();
        NLog.setPluginLogger(plugin.getLogger());
        NLog.setServerLogger(server.getLogger());
        Nitrogen.pluginName = plugin.getDescription().getName();
        Nitrogen.pluginVersion = plugin.getDescription().getVersion();
    }

    @Override
    public void onEnable()
    {
        build.load(Nitrogen.plugin);
        warnVersion();
        new Metrics(this);
        // Listeners
        server.getPluginManager().registerEvents(new ConfigListener(), Nitrogen.plugin);
        server.getPluginManager().registerEvents(new Monitors(), Nitrogen.plugin);
        // Exploits
        server.getPluginManager().registerEvents(new Potion(), Nitrogen.plugin);
        server.getPluginManager().registerEvents(new Sign(), Nitrogen.plugin);
        Config.loadConfigs();
        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                CMD_Loader.getCommandMap();
                CMD_Loader.scan();
            }
        }.runTaskLater(plugin, 20L);
        Bukkit.getScheduler().scheduleSyncRepeatingTask(Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("Nitrogen")), () ->
        {
            if (NUtil.NEntityWiper.wipeEntities(true, true) == 1)
            {
                NLog.info(NUtil.NEntityWiper.wipeEntities(true, true) + " entity removed");
            }
            else if (NUtil.NEntityWiper.wipeEntities(true, true) != 0)
            {
                NLog.info(NUtil.NEntityWiper.wipeEntities(true, true) + " entities removed");
            }
        }, 1L, (long)300 * 20);
        instance = this;
    }

    @Override
    public void onDisable()
    {
        try
        {
            Updater updater = new Updater();
            updater.update();
        }
        catch (NoClassDefFoundError ex)
        {
            NLog.info("Failed to check for an update.");
        }
    }

    public static Nitrogen getInstance()
    {
        return instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
    {
        return CMD_Handler.handleCommand(sender, cmd, commandLabel, args);
    }

    public static class BuildProperties
    {
        public String author;
        public String version;
        public String number;
        public String date;
        public String head;

        public void load(Nitrogen plugin)
        {
            try
            {
                final Properties props;
                try (InputStream in = plugin.getResource("build.properties"))
                {
                    props = new Properties();
                    props.load(in);
                }

                author = props.getProperty("buildAuthor", "unknown");
                version = props.getProperty("buildVersion", pluginVersion);
                number = props.getProperty("buildNumber", "1");
                date = props.getProperty("buildDate", "unknown");
                head = props.getProperty("buildHead", "unknown".replace("${git.commit.id.abbrev", "unknown"));
            }
            catch (Exception ex)
            {
                NLog.severe("Could not load build properties! Did you compile with Netbeans/Maven?");
                NLog.severe(ex);
            }
        }

        public String formattedVersion()
        {
            return build.version + "." + build.number + " (" + build.head + ")";
        }
    }

    public static void warnVersion()
    {
        final String nms = NUtil.getNMSVersion();

        if (!COMPILE_NMS_VERSION.equals(nms))
        {
            NLog.warning("Nitrogen is compiled for " + COMPILE_NMS_VERSION + " but the server is running version " + nms + "!");
            NLog.warning("This might result in unexpected behavior!");
        }
    }
}