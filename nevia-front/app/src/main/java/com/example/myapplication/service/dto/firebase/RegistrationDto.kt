package com.example.myapplication.service.dto.firebase

import com.google.gson.annotations.SerializedName

data class RegistrationDto(
    @SerializedName("email") var email: String,
    @SerializedName("token") var token: String
)