package com.example.hotelbooking.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.RecyclerView
import com.example.hotelbooking.R
import com.example.hotelbooking.models.Hotel

class HomeAdapter(private var hotelsList: List<Hotel>, private val listener: OnClicked) : RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    inner class HomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val hotelName: TextView = itemView.findViewById(R.id.hotel_name_tv)
        private val hotelRating: TextView = itemView.findViewById(R.id.hotel_rating_tv)
        private val hotelLocation: TextView = itemView.findViewById(R.id.hotel_location_tv)
        private val bookNowBtn: AppCompatButton = itemView.findViewById(R.id.book_now_btn)

        fun bind(hotel: Hotel) {
            hotelName.text = hotel.name
            hotelRating.text = "${hotel.stars} stars"
            hotelLocation.text = "${hotel.city}, ${hotel.country}"

            itemView.setOnClickListener {
                listener.onClicked(hotel)
            }

            bookNowBtn.setOnClickListener {
                listener.onBtnClicked()
            }
        }

    }

    fun addData(hotelList: List<Hotel>) {
        this.hotelsList = hotelList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        return HomeViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_hotel, parent, false)
        )
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.bind(hotelsList[position])
    }

    override fun getItemCount(): Int {
        return hotelsList.size
    }

    interface OnClicked {
        fun onClicked(hotel: Hotel)
        fun onBtnClicked()
    }
}