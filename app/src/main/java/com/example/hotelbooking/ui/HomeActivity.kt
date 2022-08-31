package com.example.hotelbooking.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hotelbooking.repository.HotelsRepository
import com.example.hotelbooking.R
import com.example.hotelbooking.adapters.HomeAdapter
import com.example.hotelbooking.factories.HotelsViewModelProviderFactory
import com.example.hotelbooking.models.Hotel
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity(), HomeAdapter.OnClicked {

    lateinit var viewModel: HotelViewModel

    private val homeAdapter by lazy { HomeAdapter(emptyList(), this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        init()
    }

    private fun init() {
        initialiseVariables()
        setViews()
        makeApiCall()
        setObservers()
    }

    private fun initialiseVariables() {
        val hotelsRepository = HotelsRepository()
        val viewModelProviderFactory = HotelsViewModelProviderFactory(hotelsRepository)

        viewModel = ViewModelProvider(this, viewModelProviderFactory)[HotelViewModel::class.java]
    }

    private fun setViews() {
        hotel_recyclerview.layoutManager = LinearLayoutManager(this)
        hotel_recyclerview.adapter = homeAdapter
    }

    private fun makeApiCall() {
        viewModel.getHotels()
    }

    private fun setObservers() {
        viewModel.hotels.observe(this) {
            homeAdapter.addData(it)
        }
    }

    companion object {
        fun newInstance(context: Context): Intent = Intent(context, HomeActivity::class.java)
    }

    override fun onClicked(hotel: Hotel) {
        val intent = Intent(this, HotelActivity::class.java)
        intent.putExtra("hotel", hotel)
        startActivity(intent)
    }
}
