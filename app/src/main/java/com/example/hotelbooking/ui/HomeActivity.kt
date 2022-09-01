package com.example.hotelbooking.ui

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hotelbooking.repository.HotelsRepository
import com.example.hotelbooking.R
import com.example.hotelbooking.adapters.HomeAdapter
import com.example.hotelbooking.factories.HotelsViewModelProviderFactory
import com.example.hotelbooking.models.Hotel
import kotlinx.android.synthetic.main.activity_home.*
import android.app.PendingIntent.getActivity as getActivity1

class HomeActivity : AppCompatActivity(), HomeAdapter.OnClicked {

    lateinit var viewModel: HotelViewModel
    lateinit var notificationManager: NotificationManager
    lateinit var notificationChannel: NotificationChannel
    lateinit var notificationBuilder: Notification.Builder

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

        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        viewModel = ViewModelProvider(this, viewModelProviderFactory)[HotelViewModel::class.java]
    }

    private fun setViews() {
        hotel_recyclerview.layoutManager = LinearLayoutManager(this)
        hotel_recyclerview.adapter = homeAdapter

        setNotification()
    }

    private fun makeApiCall() {
        viewModel.getHotels()
    }

    private fun setObservers() {
        viewModel.hotels.observe(this) {
            homeAdapter.addData(it)
        }
    }

    private fun setNotification() {
        val intent = Intent(this, HomeActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
//        val contentView = RemoteViews(packageName, R.layout.activity_home)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel = NotificationChannel("channel", "Notification Channel", NotificationManager.IMPORTANCE_HIGH)
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.GREEN
            notificationChannel.enableVibration(true)
            notificationManager.createNotificationChannel(notificationChannel)

            notificationBuilder = Notification.Builder(this, "channel")
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setLargeIcon(BitmapFactory.decodeResource(this.resources, R.drawable.ic_launcher_background))
                .setContentTitle("Hotel is Booked")
        } else {
            notificationBuilder = Notification.Builder(this)
                .setContentTitle("Hotel is Booked")
                .setContentIntent(pendingIntent)
        }
    }

    private fun showNotification() {
        notificationManager.notify(1234, notificationBuilder.build())
    }

    companion object {
        fun newInstance(context: Context): Intent = Intent(context, HomeActivity::class.java)
    }

    override fun onClicked(hotel: Hotel) {
        val intent = Intent(this, HotelActivity::class.java)
        intent.putExtra("hotel", hotel)
        startActivity(intent)
    }

    override fun onBtnClicked() {
        showNotification()
    }
}
