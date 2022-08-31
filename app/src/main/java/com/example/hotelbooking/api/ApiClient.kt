package com.example.hotelbooking.api

import com.example.hotelbooking.models.Hotel
import retrofit2.Response
import retrofit2.http.GET

interface ApiClient {

    @GET("api/hotels")
    suspend fun getHotels(): Response<MutableList<Hotel>>
}