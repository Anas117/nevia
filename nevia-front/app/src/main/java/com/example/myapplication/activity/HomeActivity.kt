package com.example.myapplication.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.example.myapplication.service.dto.UserDto
import com.example.myapplication.service.dto.firebase.RegistrationDto
import com.example.myapplication.user.HomeViewModel
import com.example.myapplication.utilities.HomeInjectorUtils
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging

class HomeActivity : AppCompatActivity() {

    private val tag = "CONSOLE"
    val context: Context = this@HomeActivity

    private lateinit var nameView: TextView
    private lateinit var usernameView: TextView
    private lateinit var emailView: TextView
    private lateinit var bookRoomButton: Button
    private lateinit var listBookingButton: Button
    private lateinit var outButton: Button
    private lateinit var emailText: TextView

    lateinit var sharedPreferences: SharedPreferences

    private lateinit var registrationDto: RegistrationDto

    private val viewModel by viewModels<HomeViewModel> {
        HomeInjectorUtils.provideHomeViewModelFactory(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_page)

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

    private fun setupUI() {
        emailText = findViewById(R.id.email)
        bookRoomButton = findViewById(R.id.book_room)
        bookRoomButton.setOnClickListener {
            val intent = Intent(context, BookRoomActivity::class.java)
            context.startActivity(intent)
        }

        listBookingButton = findViewById(R.id.list_booking)
        listBookingButton.setOnClickListener {
            val intent = Intent(context, ListBookingActivity::class.java)
            context.startActivity(intent)
        }

        outButton = findViewById(R.id.sign_out)
        outButton.setOnClickListener {
            val intent = Intent(context, MainActivity::class.java)
            val editor = sharedPreferences.edit()
            editor.remove("email")
            editor.apply()
            context.startActivity(intent)
        }
    }

    private fun onObserve(email: String) {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            val registrationDto: RegistrationDto = RegistrationDto("","")
            if (!task.isSuccessful) {
                Log.w(tag, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            } else {
                Log.i(tag, "connected at FireBase")
            }

            // Get new FCM registration token
            val token: String = task.result

            // Log and toast
            Log.d(tag, token)
            Toast.makeText(baseContext, token, Toast.LENGTH_SHORT).show()

            registrationDto.email = email
            registrationDto.token = token
            viewModel.sendRegistration(registrationDto)
        })

        viewModel.loadUser(email)

        viewModel.user.observe(this) { user -> showUser(user) }
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

}

