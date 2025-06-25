package com.example.myapplication.service.booking

import com.example.myapplication.environment.Environment
import com.example.myapplication.service.dto.BookingDto
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

object ApiBooking {

    private var apiBookingInterface: ApiBookingInterface? = null

    fun build(): ApiBookingInterface {
        val builder: Retrofit.Builder = Retrofit.Builder().baseUrl(Environment.baseUrlDispatcher)
            .addConverterFactory(GsonConverterFactory.create())

        val httpClient: OkHttpClient.Builder = OkHttpClient.Builder()
        httpClient.addInterceptor(interceptor())

        val retrofit: Retrofit = builder.client(httpClient.build()).build()
        apiBookingInterface = retrofit.create(
            ApiBookingInterface::class.java
        )

        return apiBookingInterface as ApiBookingInterface
    }

    private fun interceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }

    interface ApiBookingInterface {
        @POST("booking/book")
        suspend fun createBooking(@Body bookingDto: BookingDto)

        @GET("booking/mybooking/{email}")
        suspend fun getListBooking(@Path("email") email: String): Response<List<BookingDto>>
    }
}