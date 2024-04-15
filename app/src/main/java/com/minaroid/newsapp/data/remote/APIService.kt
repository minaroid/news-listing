package com.minaroid.newsapp.data.remote

import com.google.gson.JsonElement
import retrofit2.http.*

interface APIService {
    
    @GET
    suspend fun getRequest(@Url api: String, @HeaderMap headers: HashMap<String, String> = HashMap(), @QueryMap query: HashMap<String, Any> = HashMap()): JsonElement

}