package com.example.myapplication.service.user

import com.example.myapplication.service.dto.UserCreateDto
import com.example.myapplication.service.dto.UserDto


interface UserDataSource {
    suspend fun getUsers(): ResultRetrofit<List<UserDto>>

    suspend fun getOneUser(email: String): ResultRetrofit<UserDto>

    suspend fun checkUser(email: String): ResultRetrofit<Boolean>

    suspend fun createUser(userCreateDto: UserCreateDto): ResultRetrofit<UserDto>
}