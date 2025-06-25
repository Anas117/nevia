package com.example.myapplication.service.dto

data class BookingDto(
    var email: String = "",
    var firstName: String = "",
    var lastName: String = "",
    var startTime: String = "",
    var endTime: String = "",
    var roomName: String = "",
    var idRoom: Int = 0,
    var idUser: Int = 0
)