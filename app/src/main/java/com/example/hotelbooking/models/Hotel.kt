package com.example.hotelbooking.models

data class Hotel(
    val city: String,
    val country: String,
    val date_end: String,
    val date_start: String,
    val description: String,
    val id: String,
    val images: List<String>,
    val name: String,
    val price: Int,
    val rating: Double,
    val stars: Int
)