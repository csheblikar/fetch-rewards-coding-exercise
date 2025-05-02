package com.csheblikar.fetchapp.data.network

import com.csheblikar.fetchapp.data.model.Item
import retrofit2.http.GET

interface ApiService {
    @GET("hiring.json")
    suspend fun getItems(): List<Item>
}