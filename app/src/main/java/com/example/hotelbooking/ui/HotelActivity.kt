package com.example.hotelbooking.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hotelbooking.R
import com.example.hotelbooking.adapters.HotelAdapter
import com.example.hotelbooking.factories.HotelsViewModelProviderFactory
import com.example.hotelbooking.models.Hotel
import com.example.hotelbooking.repository.HotelsRepository
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_hotel.*

class HotelActivity : AppCompatActivity() {

    private val hotelAdapter by lazy { HotelAdapter(emptyList()) }
    lateinit var hotel: Hotel

    lateinit var viewModel: HotelViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hotel)

        hotel = intent.getSerializableExtra("hotel") as Hotel
        init()
    }

    private fun init() {
        Log.e("TAG", "${hotel.name}")
        initialiseVariables()
        makeApiCall()
        setViews()
        setObservers()
    }

    private fun initialiseVariables() {
        val hotelsRepository = HotelsRepository()
        val viewModelProviderFactory = HotelsViewModelProviderFactory(hotelsRepository)

        viewModel = ViewModelProvider(this, viewModelProviderFactory)[HotelViewModel::class.java]
    }

    private fun setViews() {
        activity_hotel_hotel_name_tv.text = hotel.name
        activity_hotel_description_tv.text = hotel.description
        activity_hotel_rating_tv.text = "${hotel.stars} stars"

        reviews_recycler_view.isNestedScrollingEnabled = false
        reviews_recycler_view.layoutManager = LinearLayoutManager(this)
        reviews_recycler_view.adapter = hotelAdapter
    }

    private fun makeApiCall() {
        viewModel.getReviews(hotel.id)
    }

    private fun setObservers() {
        viewModel.reviews.observe(this) {
            hotelAdapter.addData(it)
        }

        viewModel.reviewsError.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        fun newInstance(context: Context) : Intent{
            return Intent(context, HotelActivity::class.java)
        }
    }
}