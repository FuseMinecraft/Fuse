package us.flowdesigns.fuse;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import us.flowdesigns.commands.CMD_Handler;
import us.flowdesigns.commands.CMD_Loader;
import us.flowdesigns.exploits.Potion;
import us.flowdesigns.exploits.Sign;
import us.flowdesigns.listener.AutoUpdate;
import us.flowdesigns.listener.ConfigListener;
import us.flowdesigns.listener.LoginMessages;
import us.flowdesigns.listener.Monitors;
import us.flowdesigns.utils.NLog;
import us.flowdesigns.utils.NUtil;

import java.io.File;
import java.io.InputStream;
import java.util.Properties;

public class Fuse extends JavaPlugin {

    public static Fuse plugin;
    public static Server server;
    public static Fuse instance;

    public static final BuildProperties build = new BuildProperties();
    public static final GitProperties git = new GitProperties();
    public static final String COMPILE_NMS_VERSION = "v1_12_R1";
    public static String pluginName;
    public static String pluginVersion;

    File jarFile = this.getFile();

    @Override
    public void onLoad() {
        Fuse.plugin = this;
        Fuse.server = plugin.getServer();
        NLog.setServerLogger(server.getLogger());
        NLog.setServerLogger(server.getLogger());
        Fuse.pluginName = plugin.getDescription().getName();
        Fuse.pluginVersion = plugin.getDescription().getVersion();
    }

    @Override
    public void onEnable() {
        build.load(Fuse.plugin);
        git.load(Fuse.plugin);
        warnVersion();
        // Listeners
        server.getPluginManager().registerEvents(new AutoUpdate(), Fuse.plugin);
        server.getPluginManager().registerEvents(new ConfigListener(), Fuse.plugin);
        server.getPluginManager().registerEvents(new LoginMessages(), Fuse.plugin);
        server.getPluginManager().registerEvents(new Monitors(), Fuse.plugin);
        // Exploits
        server.getPluginManager().registerEvents(new Potion(), Fuse.plugin);
        server.getPluginManager().registerEvents(new Sign(), Fuse.plugin);
        Config.loadConfigs();
        new BukkitRunnable() {
            @Override
            public void run() {
                CMD_Loader.getCommandMap();
                CMD_Loader.scan();
            }
        }.runTaskLater(plugin, 20L);
        Bukkit.getScheduler().scheduleSyncRepeatingTask(Bukkit.getPluginManager().getPlugin("Fuse"), () -> {
            if ((NUtil.NEntityWiper.wipeEntities(true, true)) == 1)
            {
                NLog.info((NUtil.NEntityWiper.wipeEntities(true, true)) + " entity removed");
            } else if ((NUtil.NEntityWiper.wipeEntities(true, true)) != 0) {
                NLog.info((NUtil.NEntityWiper.wipeEntities(true, true)) + " entities removed");
            }
        }, 1L , (long) 300 * 20);
        instance = this;
    }

    @Override
    public void onDisable() {
    }

    public static Fuse getInstance() {
        return instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        return CMD_Handler.handleCommand(sender, cmd, commandLabel, args);
    }

    public static class BuildProperties {

        public String author;
        public String version;
        public String number;

        public void load(Fuse plugin) {
            try {
                final Properties props;
                try (InputStream in = plugin.getResource("build.properties")) {
                    props = new Properties();
                    props.load(in);
                }
                build.author = props.getProperty("build.author", build.author);
                build.version = props.getProperty("build.version", build.version);
                build.number = props.getProperty("buildNumber", build.number);
            } catch (Exception ex) {
                NLog.severe("Could not load build properties! Did you compile with NetBeans/Maven?");
                NLog.severe(ex);
            }
        }
    }

        public static class GitProperties {

            public String date;
            public String head;

            public void load(Fuse plugin) {
                try {
                    final Properties props;
                    try (InputStream in = plugin.getResource("git.properties")) {
                        props = new Properties();
                        props.load(in);
                    }
                    git.date = props.getProperty("git.build.time", git.date);
                    git.head = props.getProperty("git.commit.id.abbrev", git.head);
                } catch (Exception ex) {
                    NLog.severe("Could not load Git properties! Did you compile with NetBeans/Maven?");
                    NLog.severe(ex);
                }
            }
        }

        public String formattedVersion() {
            return build.version + "." + build.number + " (" + git.head + ")";
        }

    public static void warnVersion()
    {
        final String nms = NUtil.getNMSVersion();

        if (!COMPILE_NMS_VERSION.equals(nms))
        {
            NLog.warning("Fuse is compiled for " + COMPILE_NMS_VERSION + " but the server is running version " + nms + "!");
            NLog.warning("This might result in unexpected behavior!");
        }
    }
}