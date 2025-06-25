package com.example.myapplication.service.room

import com.example.myapplication.environment.Environment
import com.example.myapplication.service.dto.PageDto
import com.example.myapplication.service.dto.SearchRoomDto
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

object ApiRoom {

    private var apiRoomInterface: ApiRoom.ApiRoomInterface? = null

    fun build(): ApiRoom.ApiRoomInterface {
        val builder: Retrofit.Builder = Retrofit.Builder().baseUrl(Environment.baseUrlDispatcher)
            .addConverterFactory(GsonConverterFactory.create())

        val httpClient: OkHttpClient.Builder = OkHttpClient.Builder()
        httpClient.addInterceptor(interceptor())

        val retrofit: Retrofit = builder.client(httpClient.build()).build()
        apiRoomInterface = retrofit.create(
            ApiRoom.ApiRoomInterface::class.java
        )

        return apiRoomInterface as ApiRoom.ApiRoomInterface
    }

    private fun interceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }

    interface ApiRoomInterface {
        @GET("booking/availableRoom?")
        suspend fun getRoomsForBooking(@Query("start") startTime: String, @Query("end") endTime: String): Response<PageDto<SearchRoomDto>>
    }
}