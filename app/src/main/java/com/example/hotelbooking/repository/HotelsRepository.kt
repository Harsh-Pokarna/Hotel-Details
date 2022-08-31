package com.example.hotelbooking.repository

import com.example.hotelbooking.api.RetrofitInstance

class HotelsRepository {

    suspend fun getHotels() = RetrofitInstance.api.getHotels()

    suspend fun getReviews(hotelId: String) = RetrofitInstance.api.getReviews(hotelId)
}