package com.example.myapplication.service.booking

import com.example.myapplication.service.dto.BookingDto
import com.example.myapplication.service.dto.PageDto
import com.example.myapplication.service.dto.SearchRoomDto
import com.example.myapplication.service.user.ResultRetrofit

class BookingRemoteDataSource(): BookingDataSource {
    private val service = ApiBooking.build()

    override suspend fun createBooking(bookingDto: BookingDto) {
        service.createBooking(bookingDto)
    }

    override suspend fun getListBooking(email: String): ResultRetrofit<List<BookingDto>> {
        val response = service.getListBooking(email)
        return if (response.isSuccessful) {
            val responseBody = response.body()
            if (responseBody != null) {
                ResultRetrofit.Success(responseBody)
            } else {
                ResultRetrofit.Error("Wrong")
            }
        } else {
            ResultRetrofit.Error("Wrong")
        }
    }
}