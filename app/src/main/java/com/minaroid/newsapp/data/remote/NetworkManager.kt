package com.minaroid.newsapp.data.remote

import com.google.gson.GsonBuilder
import java.util.HashMap
import javax.inject.Inject

class NetworkManager @Inject constructor(private val apiService: APIService) {

    private val gson = GsonBuilder().serializeNulls().create()
    private val baseHeaders: HashMap<String, String> = HashMap()

    init {
        baseHeaders["Accept"] = "application/json"
        baseHeaders["Content-Type"] = "application/json"
    }

    suspend fun <V> getRequest(api: String, extraHeaders: HashMap<String, String> = HashMap(), query: HashMap<String, Any> = HashMap(), parseClass: Class<V>): V {
        val headers: HashMap<String, String> = HashMap()
        headers.putAll(baseHeaders)
        headers.putAll(extraHeaders)
        return gson.fromJson(apiService.getRequest(api, headers, query), parseClass)
    }

}