package com.example.myapplication.service.notification

import android.util.Log
import com.example.myapplication.service.dto.firebase.RegistrationDto
import com.example.myapplication.service.user.ResultRetrofit

class NotificationRemoteDataSource: NotificationDataSource {

    private val service = ApiNotification.build()
    private val tag = "CONSOLE"

    override suspend fun registration(registrationDto: RegistrationDto) {
        Log.i(tag, "send registration")
        service.postRegistration(registrationDto)
    }
}