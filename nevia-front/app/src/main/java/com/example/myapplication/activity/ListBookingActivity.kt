package com.example.myapplication.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.service.dto.BookingDto
import com.example.myapplication.service.dto.UserDto
import com.example.myapplication.user.ListBookingViewModel
import com.example.myapplication.user.adapter.BookingAdapter
import com.example.myapplication.utilities.ListBookingInjectorUtils

class ListBookingActivity : AppCompatActivity() {

    private val tag = "CONSOLE"
    val context: Context = this@ListBookingActivity

    private lateinit var nameView: TextView
    private lateinit var usernameView: TextView
    private lateinit var emailView: TextView

    private lateinit var homeButton: Button
    private lateinit var emailText: TextView

    lateinit var sharedPreferences: SharedPreferences

    private val viewModel by viewModels<ListBookingViewModel> {
        ListBookingInjectorUtils.provideListBookingViewModelFactory(context)
    }

    private lateinit var adapter: BookingAdapter
    private lateinit var recyclerView: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_booking)

        sharedPreferences = getSharedPreferences("PREFS_KEY", Context.MODE_PRIVATE)
        val email: String? = sharedPreferences.getString("email", "")

        if (email != null && email != "") {
            onObserve(email)
            setupUI()
        } else {
            val intent = Intent(context, MainActivity::class.java)
            val editor = sharedPreferences.edit()
            editor.remove("email")
            editor.apply()
            context.startActivity(intent)
        }
    }

    private fun onObserve(email: String) {
        viewModel.loadUser(email)
        viewModel.user.observe(this) { user -> showUser(user) }

        viewModel.loadListBooking(email)
        viewModel.bookings.observe(this, renderBookings)
    }

    @SuppressLint("SetTextI18n")
    private fun showUser(user: UserDto) {
        nameView = findViewById(R.id.name)
        usernameView = findViewById(R.id.username)
        emailView = findViewById(R.id.email)

        nameView.text = user.firstName
        usernameView.text = user.lastName
        emailView.text = user.email
    }

    //observers
    private val renderBookings = Observer<List<BookingDto>> {
        Log.v(tag, "data updated $it")
        adapter.update(it)
    }

    private fun setupUI(){
        recyclerView = findViewById(R.id.recycler_list_booking)
        adapter = BookingAdapter(viewModel.bookings.value ?: emptyList(), this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)


        homeButton = findViewById(R.id.home)
        homeButton.setOnClickListener {
            val intent = Intent(context, HomeActivity::class.java)
            context.startActivity(intent)
        }
    }
}