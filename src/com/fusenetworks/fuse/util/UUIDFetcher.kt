package com.fusenetworks.fuse.util

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.io.IOException
import java.io.InputStreamReader
import java.io.OutputStream
import java.net.HttpURLConnection
import java.net.URL
import java.util.UUID

object UUIDFetcher {

    private val PROFILE_URL = "https://api.mojang.com/profiles/minecraft"

    fun fetch(name: String): UUID? {
        try {
            val gson = GsonBuilder().create()
            val uuid: UUID
            val body = gson.toJson(name)
            val url = URL(PROFILE_URL)
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "POST"
            connection.setRequestProperty("Content-Type", "application/json")
            connection.useCaches = false
            connection.doInput = true
            connection.doOutput = true
            val stream = connection.outputStream
            stream.write(body.toByteArray())
            stream.flush()
            stream.close()
            val id = gson.fromJson<Array<FetchedUuid>>(
                    InputStreamReader(connection.inputStream),
                    Array<FetchedUuid>::class.java!!)
            if (id.size == 0) {
                return null
            }

            val idd = id[0].id

            uuid = UUID.fromString(idd!!.substring(0, 8) + "-" + idd.substring(8, 12)
                    + "-" + idd.substring(12, 16) + "-" + idd.substring(16, 20) + "-"
                    + idd.substring(20, 32))
            return uuid
        } catch (ex: IOException) {
            NLog.severe(ex)
        }

        return null
    }

    private object FetchedUuid {

        val id: String? = null
    }
}