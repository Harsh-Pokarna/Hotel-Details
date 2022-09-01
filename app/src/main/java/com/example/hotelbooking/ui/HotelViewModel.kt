package com.example.hotelbooking.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hotelbooking.repository.HotelsRepository
import com.example.hotelbooking.models.Hotel
import com.example.hotelbooking.models.Review
import kotlinx.coroutines.launch
import kotlin.Exception

class HotelViewModel(
    private val hotelsRepository: HotelsRepository
) : ViewModel() {

    val hotels: MutableLiveData<List<Hotel>> = MutableLiveData()
    val reviews: MutableLiveData<List<Review>> = MutableLiveData()

    fun getHotels() = viewModelScope.launch {
        val response = hotelsRepository.getHotels()
        if (response.isSuccessful) {
            response.body().let {
                hotels.postValue(it)
            }
        } else {
            Log.e("TAG", response.message())
        }
    }

    fun getReviews(hotelId: String) = viewModelScope.launch {
        val response = hotelsRepository.getReviews(hotelId)
        if (response.isSuccessful) {
            response.body().let {
                reviews.postValue(it)
            }
        } else {
            throw Exception(response.message())
        }
    }

}