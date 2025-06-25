package com.example.myapplication.service.booking

import com.example.myapplication.service.dto.BookingDto
import com.example.myapplication.service.user.ResultRetrofit
import retrofit2.Response
import retrofit2.http.Path

interface BookingDataSource {
    suspend fun createBooking(bookingDto: BookingDto)
    suspend fun getListBooking(@Path("email") email: String): ResultRetrofit<List<BookingDto>>
}