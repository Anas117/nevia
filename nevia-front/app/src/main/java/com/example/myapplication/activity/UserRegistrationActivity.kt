package com.example.myapplication.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.example.myapplication.dao.database.entity.User
import com.example.myapplication.service.dto.UserCreateDto
import com.example.myapplication.service.dto.UserDto
import com.example.myapplication.user.MainPageViewModel
import com.example.myapplication.user.UserRegistrationViewModel
import com.example.myapplication.user.UserViewModel
import com.example.myapplication.utilities.MainPageInjectorUtils
import com.example.myapplication.utilities.UserInjectorUtils
import com.example.myapplication.utilities.UserRegistrationInjectorUtils
import com.google.android.material.textfield.TextInputEditText


class UserRegistrationActivity : AppCompatActivity() {
    private lateinit var nameView: TextView
    private lateinit var usernameView: TextView
    private lateinit var buttonBack: Button

    private lateinit var buttonRegister: Button
    private lateinit var emailText: TextInputEditText
    private lateinit var firstnameText: TextInputEditText
    private lateinit var lastnameText: TextInputEditText

    lateinit var sharedPreferences: SharedPreferences

    val context: Context = this@UserRegistrationActivity


    private val viewModel by viewModels<UserRegistrationViewModel> {
        UserRegistrationInjectorUtils.provideUserRegistrationViewModelFactory(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_registration)

        setupUI()
    }

    private fun setupUI() {
        emailText = findViewById(R.id.email_registration)
        buttonRegister = findViewById(R.id.validate_registration)
        firstnameText = findViewById(R.id.firstname_registration)
        lastnameText = findViewById(R.id.lastname_registration)

        buttonRegister.setOnClickListener {
            viewModel.checkUser(emailText.text.toString())

            viewModel.checkUser.observe(this) { boolean ->
                if (!boolean) {
                    val userDto: UserCreateDto = UserCreateDto(firstnameText.text.toString(),
                        lastnameText.text.toString(),
                        emailText.text.toString());
                    viewModel.createUser(userDto)
                    val intent = Intent(context, MainActivity::class.java)
                    context.startActivity(intent)
                }
            }
        }
    }
}