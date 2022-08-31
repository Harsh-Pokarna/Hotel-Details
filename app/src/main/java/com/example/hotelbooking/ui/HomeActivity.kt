package com.example.hotelbooking.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.hotelbooking.HotelsRepository
import com.example.hotelbooking.R
import com.example.hotelbooking.factories.HotelsViewModelProviderFactory

class HomeActivity : AppCompatActivity() {

    lateinit var viewModel: HotelViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        init()
    }

    private fun init() {
        initialiseVariables()
        makeApiCall()
        setObservers()
    }

    private fun initialiseVariables() {
        val hotelsRepository = HotelsRepository()
        val viewModelProviderFactory = HotelsViewModelProviderFactory(hotelsRepository)

        viewModel = ViewModelProvider(this, viewModelProviderFactory)[HotelViewModel::class.java]
    }

    private fun makeApiCall() {
        viewModel.getHotels()
    }

    private fun setObservers() {
        viewModel.hotels.observe(this, Observer {
            println(it)
        })
    }

    companion object {
        fun newInstance(context: Context): Intent = Intent(context, HomeActivity::class.java)
    }
}
