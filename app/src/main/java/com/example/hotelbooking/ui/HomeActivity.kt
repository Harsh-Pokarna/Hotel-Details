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
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView.OnEditorActionListener
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hotelbooking.R
import com.example.hotelbooking.adapters.HomeAdapter
import com.example.hotelbooking.factories.HotelsViewModelProviderFactory
import com.example.hotelbooking.models.Hotel
import com.example.hotelbooking.repository.HotelsRepository
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import kotlinx.android.synthetic.main.activity_home.*


class HomeActivity : AppCompatActivity(), HomeAdapter.OnClicked {

    lateinit var viewModel: HotelViewModel
    lateinit var notificationManager: NotificationManager
    lateinit var notificationChannel: NotificationChannel
    lateinit var notificationBuilder: Notification.Builder

    private val homeAdapter by lazy { HomeAdapter(emptyList(), this) }

    private var hotelsList: List<Hotel> = emptyList()

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
        setListeners()
    }

    private fun initialiseVariables() {
        val hotelsRepository = HotelsRepository()
        val viewModelProviderFactory = HotelsViewModelProviderFactory(hotelsRepository)

        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        viewModel = ViewModelProvider(this, viewModelProviderFactory)[HotelViewModel::class.java]
    }

    private fun setViews() {
        search_hotel_et.imeOptions = EditorInfo.IME_ACTION_DONE
        hotel_recyclerview.layoutManager = LinearLayoutManager(this)
        hotel_recyclerview.adapter = homeAdapter

        setNotification()
    }

    private fun makeApiCall() {
        viewModel.getHotels()
    }

    private fun setObservers() {
        viewModel.hotels.observe(this) {
            main_home_layout.visibility = View.VISIBLE
            home_loading_indicator.visibility = View.GONE
            hotelsList = it
            homeAdapter.addData(hotelsList)
        }
    }

    private fun setListeners() {
        search_hotel_et.setOnEditorActionListener { v, actionId, event ->
            val filteredList: MutableList<Hotel> = mutableListOf()
            if (event != null && event.keyCode == KeyEvent.KEYCODE_ENTER || actionId == EditorInfo.IME_ACTION_DONE) {
                for (hotel in hotelsList) {
                    if (hotel.name.contains(search_hotel_et.text)) {
                        filteredList.add(hotel)
                    }
                }
                homeAdapter.addData(filteredList)
            }
            false
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.sigin_out_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.sign_out_btn -> {
                GoogleSignIn.getClient(applicationContext, GoogleSignInOptions.DEFAULT_SIGN_IN).signOut()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }
        return true
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
