package me.telesphoreo.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLConnection;
import me.telesphoreo.Nitrogen;
import org.bukkit.plugin.java.JavaPlugin;

public class Updater
{
    private Nitrogen plugin = Nitrogen.plugin;
    private Nitrogen.BuildProperties build = Nitrogen.build;
    private String oldHead = build.head;
    private String path = this.getFilePath();

    public void update()
    {
        try
        {
            String versionLink = "https://www.telesphoreo.me/nitrogen/version.txt";
            URL url = new URL(versionLink);
            URLConnection con = url.openConnection();
            InputStreamReader isr = new InputStreamReader(con.getInputStream());
            BufferedReader reader = new BufferedReader(isr);
            if (!reader.ready())
            {
                return;
            }

            String newHead = reader.readLine();
            reader.close();

            if (oldHead.equals("${git.commit.id.abbrev}") || oldHead.equals("unknown"))
            {
                NLog.info("No Git head detected, not updating Nitrogen.");
                return;
            }

            if (!newHead.equals(oldHead))
            {
                String dlLink = "https://telesphoreo.me/nitrogen/Nitrogen.jar";
                url = new URL(dlLink);
                con = url.openConnection();
                InputStream in = con.getInputStream();
                FileOutputStream out = new FileOutputStream(path);
                byte[] buffer = new byte[1024];
                int size = 0;
                while ((size = in.read(buffer)) != -1)
                {
                    out.write(buffer, 0, size);
                }

                out.close();
                in.close();
                NLog.info("An update has been successfully been applied to Nitrogen.");
            }
        }
        catch (IOException ex)
        {
            NLog.info("There was an issue fetching the server for an update.");
        }
    }

    private String getFilePath()
    {
        if (plugin != null)
        {
            try
            {
                Method method = JavaPlugin.class.getDeclaredMethod("getFile");
                boolean wasAccessible = method.isAccessible();
                method.setAccessible(true);
                File file = (File)method.invoke(plugin);
                method.setAccessible(wasAccessible);

                return file.getPath();
            }
            catch (Exception e)
            {
                return "plugins" + File.separator + plugin.getName();
            }
        }
        else
        {
            return "plugins" + File.separator + "Nitrogen.jar";
        }
    }
}