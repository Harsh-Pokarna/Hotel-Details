package com.example.hotelbooking.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hotelbooking.R
import com.example.hotelbooking.models.Review
import com.example.hotelbooking.ui.HotelActivity

class HotelAdapter(private var reviewsList: List<Review>) : RecyclerView.Adapter<HotelAdapter.HotelViewHolder>() {

    inner class HotelViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val reviewerName: TextView = itemView.findViewById(R.id.reviewer_name_tv)
        private val reviewTextView: TextView = itemView.findViewById(R.id.review_tv)

        fun bind(review: Review) {
            reviewerName.text = review.name
            reviewTextView.text = review.comment
        }
    }

    fun addData(reviewsList: List<Review>) {
        this.reviewsList = reviewsList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HotelViewHolder {
        return HotelViewHolder(
                    LayoutInflater.from(parent.context).inflate(R.layout.item_review, parent, false)
        )
    }

    override fun onBindViewHolder(holder: HotelViewHolder, position: Int) {
        holder.bind(reviewsList[position])
    }

    override fun getItemCount(): Int {
        return reviewsList.size
    }
}