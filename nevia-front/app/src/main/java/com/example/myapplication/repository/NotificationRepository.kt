package com.example.myapplication.repository

import com.example.myapplication.service.dto.firebase.RegistrationDto
import com.example.myapplication.service.notification.NotificationRemoteDataSource

class NotificationRepository(private val notificationRemoteDataSource: NotificationRemoteDataSource) {
    private val tag = "CONSOLE"

    suspend fun registration(registrationDto: RegistrationDto){
        notificationRemoteDataSource.registration(registrationDto)
    }
}