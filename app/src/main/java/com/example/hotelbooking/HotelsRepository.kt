package com.example.hotelbooking

import com.example.hotelbooking.api.RetrofitInstance

class HotelsRepository {

    suspend fun getHotels() = RetrofitInstance.api.getHotels()
}