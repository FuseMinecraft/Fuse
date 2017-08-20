package com.fusenetworks.fuse.util


import java.util.logging.Level
import java.util.logging.Logger

class NLog private constructor() {
    init {
        throw AssertionError()
    }

    companion object {
        private val FALLBACK_LOGGER = Logger.getLogger("Minecraft-Server")
        private var serverLogger: Logger? = null
        private var pluginLogger: Logger? = null
        @JvmOverloads
        fun info(message: String, raw: Boolean? = false) {
            log(Level.INFO, message, raw!!)
        }

        fun info(ex: Throwable) {
            log(Level.INFO, ex)
        }

        @JvmOverloads
        fun warning(message: String, raw: Boolean? = false) {
            log(Level.WARNING, message, raw!!)
        }

        fun warning(ex: Throwable) {
            log(Level.WARNING, ex)
        }

        @JvmOverloads
        fun severe(message: String, raw: Boolean? = false) {
            log(Level.SEVERE, message, raw!!)
        }

        fun severe(ex: Throwable) {
            log(Level.SEVERE, ex)
        }

        // Utility
        private fun log(level: Level, message: String, raw: Boolean) {
            getLogger(raw).log(level, message)
        }

        private fun log(level: Level, throwable: Throwable) {
            getLogger(false).log(level, null, throwable)
        }

        fun setServerLogger(logger: Logger) {
            serverLogger = logger
        }

        fun setPluginLogger(logger: Logger) {
            pluginLogger = logger
        }

        private fun getLogger(raw: Boolean): Logger {
            return if (raw || pluginLogger == null) {
                if (serverLogger != null) serverLogger else FALLBACK_LOGGER
            } else {
                pluginLogger
            }
        }

        fun getPluginLogger(): Logger {
            return if (pluginLogger != null) pluginLogger else FALLBACK_LOGGER
        }

        fun getServerLogger(): Logger {
            return if (serverLogger != null) serverLogger else FALLBACK_LOGGER
        }
    }
}// Level.INFO:
// Level.WARNING:
// Level.SEVERE: