package com.example.myapplication.service.user

import com.example.myapplication.environment.Environment
import com.example.myapplication.service.dto.UserCreateDto
import com.example.myapplication.service.dto.UserDto
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

object ApiUser {

    private var apiUserInterface: ApiUserInterface? = null

    fun build(): ApiUserInterface {
        val builder: Retrofit.Builder = Retrofit.Builder().baseUrl(Environment.baseUrlDispatcher)
            .addConverterFactory(GsonConverterFactory.create())

        val httpClient: OkHttpClient.Builder = OkHttpClient.Builder()
        httpClient.addInterceptor(interceptor())

        val retrofit: Retrofit = builder.client(httpClient.build()).build()
        apiUserInterface = retrofit.create(
            ApiUserInterface::class.java
        )

        return apiUserInterface as ApiUserInterface
    }

    private fun interceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }

    interface ApiUserInterface {
        @GET("user")
        suspend fun getUsers(): Response<List<UserDto>>

        @GET("user/getUser/{email}")
        suspend fun getOneUser(@Path(value = "email") email: String): Response<UserDto>

        @GET("user/checkUser/{email}")
        suspend fun checkUser(@Path(value = "email") email : String): Response<Boolean>

        @POST("user")
        suspend fun createUser(@Body userCreateDto: UserCreateDto): Response<UserDto>
    }
}