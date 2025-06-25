package com.example.myapplication.repository

import com.example.myapplication.service.dto.PageDto
import com.example.myapplication.service.dto.SearchRoomDto
import com.example.myapplication.service.room.RoomRemoteDataSource
import com.example.myapplication.service.user.ResultRetrofit

class RoomRepository(
    private val roomRemoteDataSource: RoomRemoteDataSource
) {
    private val tag = "CONSOLE"

    suspend fun getRoomForBooking(startTime: String, endTime: String): ResultRetrofit<PageDto<SearchRoomDto>>{
        val response = roomRemoteDataSource.getRoomsForBooking(startTime, endTime)
        return if (response.data != null) {
            ResultRetrofit.Success(response.data)
        } else {
            ResultRetrofit.Error("Wrong")
        }
    }
}