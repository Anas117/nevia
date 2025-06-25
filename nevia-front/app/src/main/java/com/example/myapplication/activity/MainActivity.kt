package com.example.myapplication.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.example.myapplication.user.MainPageViewModel
import com.example.myapplication.utilities.MainPageInjectorUtils
import com.google.android.material.textfield.TextInputEditText


class MainActivity : AppCompatActivity() {

    private val tag = "CONSOLE"
    val context: Context = this@MainActivity
    private lateinit var buttonConnexion: Button
    private lateinit var buttonRegister: Button
    private lateinit var emailText: TextInputEditText

    // variable to stock globals variables
    lateinit var sharedPreferences: SharedPreferences

    private val viewModel by viewModels<MainPageViewModel> {
        MainPageInjectorUtils.provideMainPageViewModelFactory(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupUI()
    }

    private fun setupUI() {
        emailText = findViewById(R.id.email)
        buttonRegister = findViewById(R.id.registration)
        buttonConnexion = findViewById(R.id.connexion)


        buttonRegister.setOnClickListener{
            val intent = Intent(context, UserRegistrationActivity::class.java)
            context.startActivity(intent)
        }

        buttonConnexion.setOnClickListener {
            viewModel.checkUser(emailText.text.toString())

            viewModel.checkUser.observe(this) { boolean ->
                if (boolean) {
                    sharedPreferences = getSharedPreferences("PREFS_KEY", Context.MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    //insert values
                    editor.putString("email", emailText.text.toString())
                    // write changements
                    editor.apply()

                    val intent = Intent(context, HomeActivity::class.java)
                    context.startActivity(intent)
                }
            }
        }
    }
}
