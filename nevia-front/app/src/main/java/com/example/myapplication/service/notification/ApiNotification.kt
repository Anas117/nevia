package com.example.myapplication.service.notification

import com.example.myapplication.environment.Environment
import com.example.myapplication.service.dto.firebase.RegistrationDto
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

object ApiNotification {

    private var apiNotificationInterface: ApiNotificationInterface? = null

    fun build(): ApiNotificationInterface {
        val builder: Retrofit.Builder = Retrofit.Builder().baseUrl(Environment.baseUrlNotification)
            .addConverterFactory(GsonConverterFactory.create())

        val httpClient: OkHttpClient.Builder = OkHttpClient.Builder()
        httpClient.addInterceptor(interceptor())

        val retrofit: Retrofit = builder.client(httpClient.build()).build()
        apiNotificationInterface = retrofit.create(
            ApiNotificationInterface::class.java
        )

        return apiNotificationInterface as ApiNotificationInterface
    }

    private fun interceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }

    interface ApiNotificationInterface {
        @POST("registration")
        suspend fun postRegistration(@Body registrationDto: RegistrationDto)
    }
}