package com.example.myapplication.repository

import com.example.myapplication.service.booking.BookingRemoteDataSource
import com.example.myapplication.service.dto.BookingDto
import com.example.myapplication.service.dto.PageDto
import com.example.myapplication.service.dto.SearchRoomDto
import com.example.myapplication.service.user.ResultRetrofit

class BookingRepository(private val bookingRemoteDataSource: BookingRemoteDataSource) {
    private val tag = "CONSOLE"

    suspend fun createBooking(bookingDto: BookingDto){
        bookingRemoteDataSource.createBooking(bookingDto)
    }

    suspend fun getRoomForBooking(email: String): ResultRetrofit<List<BookingDto>> {
        val response = bookingRemoteDataSource.getListBooking(email)
        return if (response.data != null) {
            ResultRetrofit.Success(response.data)
        } else {
            ResultRetrofit.Error("Wrong")
        }
    }
}