package com.fusenetworks.fuse

import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.util.EnumMap
import org.bukkit.Bukkit
import org.bukkit.configuration.InvalidConfigurationException
import org.bukkit.configuration.file.YamlConfiguration

object Config {

    private val config = EnumMap<ConfigFile, YamlConfiguration>(ConfigFile::class.java)
    private val configFile = EnumMap<ConfigFile, File>(ConfigFile::class.java)
    private val loaded = EnumMap<ConfigFile, Boolean>(ConfigFile::class.java)

    fun getConfig(configfile: ConfigFile): YamlConfiguration? {
        if (loaded.containsKey(configfile)) {
            loadConfig(configfile)
        }
        return config[configfile]
    }

    fun getConfigFile(configfile: ConfigFile): File? = configFile[configfile]

    fun getLoaded(configfile: ConfigFile): Boolean? {
        return loaded[configfile]
    }

    fun loadConfigs() {
        for (configfile in ConfigFile.values()) {
            loadConfig(configfile)
        }
    }

    fun loadConfig(configfile: ConfigFile) {
        configFile.put(configfile, File(Bukkit.getServer().pluginManager.getPlugin("Fuse").dataFolder, configfile.file))
        var any = if (configFile.get(configfile).exists()) {
            config.put(configfile, YamlConfiguration())
            try {
                config[configfile].load(configFile[configfile])
            } catch (ex: FileNotFoundException) {
                loaded.put(configfile, false)
                return
            } catch (ex: IOException) {
                loaded.put(configfile, false)
                return
            } catch (ex: InvalidConfigurationException) {
                loaded.put(configfile, false)
                return
            }

            loaded.put(configfile, true)
        } else {
            try {
                Bukkit.getServer().pluginManager.getPlugin("Fuse").dataFolder.mkdir()
                val jarURL = Fuse::class.java.getResourceAsStream("/" + configfile.file)
                copyFile(jarURL, configFile[configfile])
                config.put(configfile, YamlConfiguration())
                config[configfile].load(configFile[configfile])
                loaded.put(configfile, true)
            } catch (e: Exception) {
            }

        }
    }

    @Throws(Exception::class)
    private fun copyFile(`in`: InputStream?, out: File) {
        val fos = FileOutputStream(out)
        try {
            val buf = ByteArray(1024)
            var i = 0
            while ((i = `in`!!.read(buf)) != -1) {
                fos.write(buf, 0, i)
            }
        } catch (e: Exception) {
            throw e
        } finally {
            `in`?.close()
            fos?.close()
        }
    }

    enum class ConfigFile
    /**
     * Constructor of ConfigFile.
     * @param file
     */
    private constructor(// Variables
            /**
             * Gets the file associated with the enum.
             *
             * @return File associated wiht the enum
             */
            val file: String) {
        // Enums
        CONFIG("config.yml"),
        HELP("help.yml")
    }
}