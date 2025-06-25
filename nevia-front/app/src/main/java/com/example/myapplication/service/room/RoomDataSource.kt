package com.example.myapplication.service.room

import com.example.myapplication.service.dto.PageDto
import com.example.myapplication.service.dto.SearchRoomDto
import com.example.myapplication.service.user.ResultRetrofit

interface RoomDataSource {

    suspend fun getRoomsForBooking(startTime: String, endTime: String): ResultRetrofit<PageDto<SearchRoomDto>>
}