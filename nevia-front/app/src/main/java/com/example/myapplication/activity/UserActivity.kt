package com.example.myapplication.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.example.myapplication.dao.database.entity.User
import com.example.myapplication.service.dto.UserDto
import com.example.myapplication.user.UserViewModel
import com.example.myapplication.utilities.UserInjectorUtils


class UserActivity : AppCompatActivity() {
    private lateinit var nameView: TextView
    private lateinit var usernameView: TextView
    private lateinit var buttonBack: Button

    val context: Context = this@UserActivity

    private val viewModel by viewModels<UserViewModel> {
        UserInjectorUtils.provideUserViewModelFactory(context)
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        val idUser: String? = intent.getStringExtra("idUser")
        if (idUser != null) {
            onObserve(idUser)
            setupUI()
        }
    }

    private fun setupUI() {
        buttonBack = findViewById(R.id.button_come_back)
        buttonBack.setOnClickListener {
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }
    }

    private fun onObserve(idUser: String) {
        viewModel.loadUser(idUser)
        viewModel.user.observe(this) { user -> showUser(user) }

    }

    @SuppressLint("SetTextI18n")
    private fun showUser(user: UserDto) {
        nameView = findViewById(R.id.name)
        usernameView = findViewById(R.id.username)

        nameView.text = "Name  " + user.firstName
        usernameView.text = "Username " + user.lastName
    }
}