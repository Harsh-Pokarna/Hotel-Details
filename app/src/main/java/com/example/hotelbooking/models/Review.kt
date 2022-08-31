package com.example.hotelbooking.models

data class Review(
    val comment: String,
    val hotel_id: String,
    val name: String,
    val positive: Boolean
)