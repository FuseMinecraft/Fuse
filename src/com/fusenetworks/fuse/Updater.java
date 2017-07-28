package com.fusenetworks.fuse;

import com.fusenetworks.fuse.util.NUtil;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import static org.bukkit.Bukkit.getPlayer;
import static org.bukkit.Bukkit.getServer;

public class Updater {
	final String dlLink = "http://vps76574.vps.ovh.ca/Fuse.jar";
	final String versionLink = "http://vps76574.vps.ovh.ca/version.txt";
	private Plugin plugin;
	
	public Updater (Plugin plugin) {
		this.plugin = plugin;
	}

	public void checkUpdate() {
			{
				int oldVersion = this.getVersionFromString(plugin.getDescription().getVersion());
				String path = this.getFilePath();

				try {
					URL url = new URL(versionLink);
					URLConnection con = url.openConnection();
					InputStreamReader isr = new InputStreamReader(con.getInputStream());
					BufferedReader reader = new BufferedReader(isr);
					reader.ready();
					int newVersion = this.getVersionFromString(reader.readLine());
					reader.close();

					if (newVersion > oldVersion) {
						plugin.getLogger().log(Level.INFO, "There is an update available for Fuse");
						if (Bukkit.getPlayer(Bukkit.getName()).hasPermission("fuse.update") || Bukkit.getPlayer(Bukkit.getName()).isOp())
						{
							Bukkit.getPlayer(Bukkit.getName()).sendMessage(ChatColor.RED + "[Fuse] There is an update available for Fuse. To update, please type /fuse update" );
						}
					}
				} catch (IOException e) {
					plugin.getLogger().log(Level.SEVERE, "Failed to automatically check for updates", e);
				}
			}
		}

	public void update(CommandSender sender)
	{
		int oldVersion = this.getVersionFromString(plugin.getDescription().getVersion());
		String path = this.getFilePath();
		
		try
		{
			URL url = new URL(versionLink);
			URLConnection con = url.openConnection();
			InputStreamReader isr = new InputStreamReader(con.getInputStream());
			BufferedReader reader = new BufferedReader(isr);
            reader.ready();
			int newVersion = this.getVersionFromString(reader.readLine());
			reader.close();
			
				if (newVersion > oldVersion)
				{
					url = new URL(dlLink);
					con = url.openConnection();
					InputStream in = con.getInputStream();
					FileOutputStream out = new FileOutputStream(path);
					byte[] buffer = new byte[1024];
					int size = 0;
					while((size = in.read(buffer)) != -1) {
					out.write(buffer, 0, size);
				}

				out.close();
				in.close();
            	plugin.getLogger().log(Level.INFO, "Updating to the latest version of Fuse");
            	NUtil.bcastMsg(sender.getName() + " - Updating to the latest version of Fuse", ChatColor.BLUE);
            	NUtil.bcastMsg(ChatColor.BLUE + "Please wait.");
            	Bukkit.reload();
            	NUtil.bcastMsg(ChatColor.BLUE + "Update successful.");
				}
				else
				{
					sender.sendMessage(ChatColor.GRAY + "There are no updates available for Fuse.");
				}
		} catch (IOException e)
		{
			plugin.getLogger().log(Level.SEVERE, "Failed to auto-update", e);
		}
	}
	
	private String getFilePath()
	{
		if (plugin instanceof JavaPlugin)
		{
			try
			{
				Method method = JavaPlugin.class.getDeclaredMethod("getFile");
				boolean wasAccessible = method.isAccessible();
				method.setAccessible(true);
				File file = (File) method.invoke(plugin);
				method.setAccessible(wasAccessible);
				
				return file.getPath();
			} catch (Exception e) {
				return "plugins" + File.separator + plugin.getName();
			}
		} else {
			return "plugins" + File.separator + plugin.getName();
		}
	}
	
	private int getVersionFromString(String from)
	{
		String result = "";
		Pattern pattern = Pattern.compile("\\d+");
		Matcher matcher = pattern.matcher(from);
		
		while(matcher.find())
		{
			result += matcher.group();
		}
		
		return result.isEmpty() ? 0 : Integer.parseInt(result);
	}
}