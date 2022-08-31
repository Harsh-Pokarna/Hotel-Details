package com.example.hotelbooking.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.hotelbooking.repository.HotelsRepository
import com.example.hotelbooking.ui.HotelViewModel

class HotelsViewModelProviderFactory(
    private val hotelsRepository: HotelsRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HotelViewModel(hotelsRepository) as T
    }
}