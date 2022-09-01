package com.example.hotelbooking.api

import com.example.hotelbooking.models.Hotel
import com.example.hotelbooking.models.Review
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiClient {

    @GET("api/hotels")
    suspend fun getHotels(
        @Query("force_error")
        forceError: Int = 1
    ): Response<MutableList<Hotel>>

    @GET("api/reviews")
    suspend fun getReviews(
        @Query("hotel_id")
        hotelId: String
        ): Response<MutableList<Review>>
}