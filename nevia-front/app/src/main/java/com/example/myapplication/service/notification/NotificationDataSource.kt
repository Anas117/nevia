package com.example.myapplication.service.notification

import com.example.myapplication.service.dto.firebase.RegistrationDto

interface NotificationDataSource {
    suspend fun registration(registrationDto: RegistrationDto)
}