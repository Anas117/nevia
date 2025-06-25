package com.example.myapplication.service.room

import com.example.myapplication.service.dto.PageDto
import com.example.myapplication.service.dto.SearchRoomDto
import com.example.myapplication.service.user.ResultRetrofit

class RoomRemoteDataSource : RoomDataSource {

    private val service = ApiRoom.build()

    override suspend fun getRoomsForBooking(startTime: String, endTime: String): ResultRetrofit<PageDto<SearchRoomDto>> {
        val response = service.getRoomsForBooking(startTime, endTime)
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