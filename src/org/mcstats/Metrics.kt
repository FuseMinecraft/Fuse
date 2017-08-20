/*
 * Copyright 2011-2013 Tyler Blair. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 *
 *    1. Redistributions of source code must retain the above copyright notice, this list of
 *       conditions and the following disclaimer.
 *
 *    2. Redistributions in binary form must reproduce the above copyright notice, this list
 *       of conditions and the following disclaimer in the documentation and/or other materials
 *       provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR ''AS IS'' AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE AUTHOR OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * The views and conclusions contained in the software and documentation are those of the
 * authors and contributors and should not be interpreted as representing official policies,
 * either expressed or implied, of anybody else.
 */
package org.mcstats

import org.bukkit.Bukkit
import org.bukkit.Server
import org.bukkit.configuration.InvalidConfigurationException
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.PluginDescriptionFile
import org.bukkit.scheduler.BukkitTask

import java.io.BufferedReader
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.io.InputStreamReader
import java.io.OutputStream
import java.io.UnsupportedEncodingException
import java.lang.reflect.Method
import java.net.Proxy
import java.net.URL
import java.net.URLConnection
import java.net.URLEncoder
import java.util.Collections
import java.util.HashSet
import java.util.LinkedHashSet
import java.util.UUID
import java.util.logging.Level
import java.util.zip.GZIPOutputStream

class Metrics @Throws(IOException::class)
constructor(
        /**
         * The plugin this metrics submits for
         */
        private val plugin: Plugin?) {

    /**
     * All of the custom graphs to submit to metrics
     */
    private val graphs = Collections.synchronizedSet(HashSet<Graph>())

    /**
     * The plugin configuration file
     */
    private val configuration: YamlConfiguration

    /**
     * The plugin configuration file
     */
    private val configurationFile: File

    /**
     * Unique server id
     */
    private val guid: String

    /**
     * Debug mode
     */
    private val debug: Boolean

    /**
     * Lock for synchronization
     */
    private val optOutLock = Any()

    /**
     * The scheduled task
     */
    @Volatile private var task: BukkitTask? = null

    init {
        if (plugin == null) {
            throw IllegalArgumentException("Plugin cannot be null")
        }

        // load the config
        configurationFile = configFile
        configuration = YamlConfiguration.loadConfiguration(configurationFile)

        // add some defaults
        configuration.addDefault("opt-out", false)
        configuration.addDefault("guid", UUID.randomUUID().toString())
        configuration.addDefault("debug", false)

        // Do we need to create the file?
        if (configuration.get("guid", null) == null) {
            configuration.options().header("http://mcstats.org").copyDefaults(true)
            configuration.save(configurationFile)
        }

        // Load the guid then
        guid = configuration.getString("guid")
        debug = configuration.getBoolean("debug", false)
    }

    /**
     * Construct and create a Graph that can be used to separate specific plotters to their own graphs on the metrics
     * website. Plotters can be added to the graph object returned.
     *
     * @param name The name of the graph
     * @return Graph object created. Will never return NULL under normal circumstances unless bad parameters are given
     */
    fun createGraph(name: String?): Graph {
        if (name == null) {
            throw IllegalArgumentException("Graph name cannot be null")
        }

        // Construct the graph object
        val graph = Graph(name)

        // Now we can add our graph
        graphs.add(graph)

        // and return back
        return graph
    }

    /**
     * Add a Graph object to BukkitMetrics that represents data for the plugin that should be sent to the backend
     *
     * @param graph The name of the graph
     */
    fun addGraph(graph: Graph?) {
        if (graph == null) {
            throw IllegalArgumentException("Graph cannot be null")
        }

        graphs.add(graph)
    }

    /**
     * Start measuring statistics. This will immediately create an async repeating task as the plugin and send the
     * initial data to the metrics backend, and then after that it will post in increments of PING_INTERVAL * 1200
     * ticks.
     *
     * @return True if statistics measuring is running, otherwise false.
     */
    fun start(): Boolean {
        synchronized(optOutLock) {
            // Did we opt out?
            if (isOptOut) {
                return false
            }

            // Is metrics already running?
            if (task != null) {
                return true
            }

            // Begin hitting the server with glorious data
            task = plugin.getServer().scheduler.runTaskTimerAsynchronously(plugin, object : Runnable {

                private var firstPost = true

                override fun run() {
                    try {
                        // This has to be synchronized or it can collide with the disable method.
                        synchronized(optOutLock) {
                            // Disable Task, if it is running and the server owner decided to opt-out
                            if (isOptOut && task != null) {
                                task!!.cancel()
                                task = null
                                // Tell all plotters to stop gathering information.
                                for (graph in graphs) {
                                    graph.onOptOut()
                                }
                            }
                        }

                        // We use the inverse of firstPost because if it is the first time we are posting,
                        // it is not a interval ping, so it evaluates to FALSE
                        // Each time thereafter it will evaluate to TRUE, i.e PING!
                        postPlugin(!firstPost)

                        // After the first post we set firstPost to false
                        // Each post thereafter will be a ping
                        firstPost = false
                    } catch (e: IOException) {
                        if (debug) {
                            Bukkit.getLogger().log(Level.INFO, "[Metrics] " + e.message)
                        }
                    }

                }
            }, 0, (PING_INTERVAL * 1200).toLong())

            return true
        }
    }

    /**
     * Has the server owner denied plugin metrics?
     *
     * @return true if metrics should be opted out of it
     */
    // Reload the metrics file
    val isOptOut: Boolean
        get() {
            synchronized(optOutLock) {
                try {
                    configuration.load(configFile)
                } catch (ex: IOException) {
                    if (debug) {
                        Bukkit.getLogger().log(Level.INFO, "[Metrics] " + ex.message)
                    }
                    return true
                } catch (ex: InvalidConfigurationException) {
                    if (debug) {
                        Bukkit.getLogger().log(Level.INFO, "[Metrics] " + ex.message)
                    }
                    return true
                }

                return configuration.getBoolean("opt-out", false)
            }
        }

    /**
     * Enables metrics for the server by setting "opt-out" to false in the config file and starting the metrics task.
     *
     * @throws java.io.IOException
     */
    @Throws(IOException::class)
    fun enable() {
        // This has to be synchronized or it can collide with the check in the task.
        synchronized(optOutLock) {
            // Check if the server owner has already set opt-out, if not, set it.
            if (isOptOut) {
                configuration.set("opt-out", false)
                configuration.save(configurationFile)
            }

            // Enable Task, if it is not running
            if (task == null) {
                start()
            }
        }
    }

    /**
     * Disables metrics for the server by setting "opt-out" to true in the config file and canceling the metrics task.
     *
     * @throws java.io.IOException
     */
    @Throws(IOException::class)
    fun disable() {
        // This has to be synchronized or it can collide with the check in the task.
        synchronized(optOutLock) {
            // Check if the server owner has already set opt-out, if not, set it.
            if (!isOptOut) {
                configuration.set("opt-out", true)
                configuration.save(configurationFile)
            }

            // Disable Task, if it is running
            if (task != null) {
                task!!.cancel()
                task = null
            }
        }
    }

    /**
     * Gets the File object of the config file that should be used to store data such as the GUID and opt-out status
     *
     * @return the File object for the config file
     */
    // I believe the easiest way to get the base folder (e.g craftbukkit set via -P) for plugins to use
    // is to abuse the plugin object we already have
    // plugin.getDataFolder() => base/plugins/PluginA/
    // pluginsFolder => base/plugins/
    // The base is not necessarily relative to the startup directory.
    // return => base/plugins/PluginMetrics/config.yml
    val configFile: File
        get() {
            val pluginsFolder = plugin.getDataFolder().parentFile
            return File(File(pluginsFolder, "PluginMetrics"), "config.yml")
        }

    /**
     * Gets the online player (backwards compatibility)
     *
     * @return online player amount
     */
    private val onlinePlayers: Int
        get() {
            try {
                val onlinePlayerMethod = Server::class.java!!.getMethod("getOnlinePlayers")
                return if (onlinePlayerMethod.getReturnType() == Collection<*>::class.java) {
                    (onlinePlayerMethod.invoke(Bukkit.getServer()) as Collection<*>).size
                } else {
                    (onlinePlayerMethod.invoke(Bukkit.getServer()) as Array<Player>).size
                }
            } catch (ex: Exception) {
                if (debug) {
                    Bukkit.getLogger().log(Level.INFO, "[Metrics] " + ex.message)
                }
            }

            return 0
        }

    /**
     * Generic method that posts a plugin to the metrics website
     */
    @Throws(IOException::class)
    private fun postPlugin(isPing: Boolean) {
        // Server software specific section
        val description = plugin.getDescription()
        val pluginName = description.name
        val onlineMode = Bukkit.getServer().onlineMode // TRUE if online mode is enabled
        val pluginVersion = description.version
        val serverVersion = Bukkit.getVersion()
        val playersOnline = this.onlinePlayers

        // END server software specific section -- all code below does not use any code outside of this class / Java

        // Construct the post data
        val json = StringBuilder(1024)
        json.append('{')

        // The plugin's description file containg all of the plugin data such as name, version, author, etc
        appendJSONPair(json, "guid", guid)
        appendJSONPair(json, "plugin_version", pluginVersion)
        appendJSONPair(json, "server_version", serverVersion)
        appendJSONPair(json, "players_online", Integer.toString(playersOnline))

        // New data as of R6
        val osname = System.getProperty("os.name")
        var osarch = System.getProperty("os.arch")
        val osversion = System.getProperty("os.version")
        val java_version = System.getProperty("java.version")
        val coreCount = Runtime.getRuntime().availableProcessors()

        // normalize os arch .. amd64 -> x86_64
        if (osarch == "amd64") {
            osarch = "x86_64"
        }

        appendJSONPair(json, "osname", osname)
        appendJSONPair(json, "osarch", osarch)
        appendJSONPair(json, "osversion", osversion)
        appendJSONPair(json, "cores", Integer.toString(coreCount))
        appendJSONPair(json, "auth_mode", if (onlineMode) "1" else "0")
        appendJSONPair(json, "java_version", java_version)

        // If we're pinging, append it
        if (isPing) {
            appendJSONPair(json, "ping", "1")
        }

        if (graphs.size > 0) {
            synchronized(graphs) {
                json.append(',')
                json.append('"')
                json.append("graphs")
                json.append('"')
                json.append(':')
                json.append('{')

                var firstGraph = true

                val iter = graphs.iterator()

                while (iter.hasNext()) {
                    val graph = iter.next()

                    val graphJson = StringBuilder()
                    graphJson.append('{')

                    for (plotter in graph.getPlotters()) {
                        appendJSONPair(graphJson, plotter.columnName, Integer.toString(plotter.value))
                    }

                    graphJson.append('}')

                    if (!firstGraph) {
                        json.append(',')
                    }

                    json.append(escapeJSON(graph.name))
                    json.append(':')
                    json.append(graphJson)

                    firstGraph = false
                }

                json.append('}')
            }
        }

        // close json
        json.append('}')

        // Create the url
        val url = URL(BASE_URL + String.format(REPORT_URL, urlEncode(pluginName)))

        // Connect to the website
        val connection: URLConnection

        // Mineshafter creates a socks proxy, so we can safely bypass it
        // It does not reroute POST requests so we need to go around it
        if (isMineshafterPresent) {
            connection = url.openConnection(Proxy.NO_PROXY)
        } else {
            connection = url.openConnection()
        }


        val uncompressed = json.toString().toByteArray()
        val compressed = gzip(json.toString())

        // Headers
        connection.addRequestProperty("User-Agent", "MCStats/" + REVISION)
        connection.addRequestProperty("Content-Type", "application/json")
        connection.addRequestProperty("Content-Encoding", "gzip")
        connection.addRequestProperty("Content-Length", Integer.toString(compressed.size))
        connection.addRequestProperty("Accept", "application/json")
        connection.addRequestProperty("Connection", "close")

        connection.doOutput = true

        if (debug) {
            println("[Metrics] Prepared request for " + pluginName + " uncompressed=" + uncompressed.size + " compressed=" + compressed.size)
        }

        // Write the data
        val os = connection.getOutputStream()
        os.write(compressed)
        os.flush()

        // Now read the response
        val reader = BufferedReader(InputStreamReader(connection.getInputStream()))
        var response: String? = reader.readLine()

        // close resources
        os.close()
        reader.close()

        if (response == null || response.startsWith("ERR") || response.startsWith("7")) {
            if (response == null) {
                response = "null"
            } else if (response.startsWith("7")) {
                response = response.substring(if (response.startsWith("7,")) 2 else 1)
            }

            throw IOException(response)
        } else {
            // Is this the first update this hour?
            if (response == "1" || response.contains("This is your first update this hour")) {
                synchronized(graphs) {
                    val iter = graphs.iterator()

                    while (iter.hasNext()) {
                        val graph = iter.next()

                        for (plotter in graph.getPlotters()) {
                            plotter.reset()
                        }
                    }
                }
            }
        }
    }

    /**
     * Check if mineshafter is present. If it is, we need to bypass it to send POST requests
     *
     * @return true if mineshafter is installed on the server
     */
    private val isMineshafterPresent: Boolean
        get() {
            try {
                Class.forName("mineshafter.MineServer")
                return true
            } catch (e: Exception) {
                return false
            }

        }

    /**
     * Represents a custom graph on the website
     */
    class Graph private constructor(
            /**
             * The graph's name, alphanumeric and spaces only :) If it does not comply to the above when submitted, it is
             * rejected
             */
            /**
             * Gets the graph's name
             *
             * @return the Graph's name
             */
            val name: String) {

        /**
         * The set of plotters that are contained within this graph
         */
        private val plotters = LinkedHashSet<Plotter>()

        /**
         * Add a plotter to the graph, which will be used to plot entries
         *
         * @param plotter the plotter to add to the graph
         */
        fun addPlotter(plotter: Plotter) {
            plotters.add(plotter)
        }

        /**
         * Remove a plotter from the graph
         *
         * @param plotter the plotter to remove from the graph
         */
        fun removePlotter(plotter: Plotter) {
            plotters.remove(plotter)
        }

        /**
         * Gets an **unmodifiable** set of the plotter objects in the graph
         *
         * @return an unmodifiable [java.util.Set] of the plotter objects
         */
        fun getPlotters(): Set<Plotter> {
            return Collections.unmodifiableSet(plotters)
        }

        override fun hashCode(): Int {
            return name.hashCode()
        }

        override fun equals(`object`: Any?): Boolean {
            if (`object` !is Graph) {
                return false
            }

            val graph = `object` as Graph?
            return graph!!.name == name
        }

        /**
         * Called when the server owner decides to opt-out of BukkitMetrics while the server is running.
         */
        fun onOptOut() {}
    }

    /**
     * Interface used to collect custom data for a plugin
     */
    abstract class Plotter
    /**
     * Construct a plotter with a specific plot name
     *
     * @param name the name of the plotter to use, which will show up on the website
     */
    @JvmOverloads constructor(
            /**
             * The plot's name
             */
            /**
             * Get the column name for the plotted point
             *
             * @return the plotted point's column name
             */
            val columnName: String = "Default") {

        /**
         * Get the current value for the plotted point. Since this function defers to an external function it may or may
         * not return immediately thus cannot be guaranteed to be thread friendly or safe. This function can be called
         * from any thread so care should be taken when accessing resources that need to be synchronized.
         *
         * @return the current value for the point to be plotted.
         */
        abstract val value: Int

        /**
         * Called after the website graphs have been updated
         */
        fun reset() {}

        override fun hashCode(): Int {
            return columnName.hashCode()
        }

        override fun equals(`object`: Any?): Boolean {
            if (`object` !is Plotter) {
                return false
            }

            val plotter = `object` as Plotter?
            return plotter!!.columnName == columnName && plotter.value == value
        }
    }

    /**
     * Construct a plotter with the default plot name
     */

    companion object {

        /**
         * The current revision number
         */
        private val REVISION = 7

        /**
         * The base url of the metrics domain
         */
        private val BASE_URL = "http://report.mcstats.org"

        /**
         * The url used to report a server's status
         */
        private val REPORT_URL = "/plugin/%s"

        /**
         * Interval of time to ping (in minutes)
         */
        private val PING_INTERVAL = 15

        /**
         * GZip compress a string of bytes
         *
         * @param input
         * @return
         */
        fun gzip(input: String): ByteArray {
            val baos = ByteArrayOutputStream()
            var gzos: GZIPOutputStream? = null

            try {
                gzos = GZIPOutputStream(baos)
                gzos.write(input.toByteArray(charset("UTF-8")))
            } catch (e: IOException) {
                e.printStackTrace()
            } finally {
                if (gzos != null)
                    try {
                        gzos.close()
                    } catch (ignore: IOException) {
                    }

            }

            return baos.toByteArray()
        }

        /**
         * Appends a json encoded key/value pair to the given string builder.
         *
         * @param json
         * @param key
         * @param value
         * @throws UnsupportedEncodingException
         */
        @Throws(UnsupportedEncodingException::class)
        private fun appendJSONPair(json: StringBuilder, key: String, value: String) {
            var isValueNumeric = false

            try {
                if (value == "0" || !value.endsWith("0")) {
                    java.lang.Double.parseDouble(value)
                    isValueNumeric = true
                }
            } catch (e: NumberFormatException) {
                isValueNumeric = false
            }

            if (json[json.length - 1] != '{') {
                json.append(',')
            }

            json.append(escapeJSON(key))
            json.append(':')

            if (isValueNumeric) {
                json.append(value)
            } else {
                json.append(escapeJSON(value))
            }
        }

        /**
         * Escape a string to create a valid JSON string
         *
         * @param text
         * @return
         */
        private fun escapeJSON(text: String): String {
            val builder = StringBuilder()

            builder.append('"')
            for (index in 0..text.length - 1) {
                val chr = text[index]

                when (chr) {
                    '"', '\\' -> {
                        builder.append('\\')
                        builder.append(chr)
                    }
                    '\b' -> builder.append("\\b")
                    '\t' -> builder.append("\\t")
                    '\n' -> builder.append("\\n")
                    '\r' -> builder.append("\\r")
                    else -> if (chr < ' ') {
                        val t = "000" + Integer.toHexString(chr.toInt())
                        builder.append("\\u" + t.substring(t.length - 4))
                    } else {
                        builder.append(chr)
                    }
                }
            }
            builder.append('"')

            return builder.toString()
        }

        /**
         * Encode text as UTF-8
         *
         * @param text the text to encode
         * @return the encoded text, as UTF-8
         */
        @Throws(UnsupportedEncodingException::class)
        private fun urlEncode(text: String): String {
            return URLEncoder.encode(text, "UTF-8")
        }
    }
}