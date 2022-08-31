package com.example.hotelbooking.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hotelbooking.HotelsRepository
import com.example.hotelbooking.models.Hotel
import kotlinx.coroutines.launch
import kotlin.Exception

class HotelViewModel(
    private val hotelsRepository: HotelsRepository
) : ViewModel() {

    val hotels: MutableLiveData<List<Hotel>> = MutableLiveData()

    fun getHotels() = viewModelScope.launch {
        val response = hotelsRepository.getHotels()
        if (response.isSuccessful) {
            response.body().let {
                hotels.postValue(it)
            }
        } else {
            throw Exception(response.message())
        }
    }

}