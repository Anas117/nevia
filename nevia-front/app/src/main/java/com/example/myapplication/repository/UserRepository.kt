package com.example.myapplication.repository

import android.util.Log
import com.example.myapplication.dao.UserLocalDataSource
import com.example.myapplication.service.dto.UserCreateDto
import com.example.myapplication.service.dto.UserDto
import com.example.myapplication.service.user.ResultRetrofit
import com.example.myapplication.service.user.UserRemoteDataSource

class UserRepository(
    private val userRemoteDataSource: UserRemoteDataSource,
    private val userLocalDataSource: UserLocalDataSource
) {
    private val tag = "CONSOLE"

    suspend fun getUsersFromInternet(): ResultRetrofit<List<UserDto>> {
        val response = userRemoteDataSource.getUsers()
        return if (response.data != null) {
            ResultRetrofit.Success(response.data)
        } else {
            ResultRetrofit.Error("Wrong")
        }
    }

    suspend fun getOneUserFromInternet(email: String): ResultRetrofit<UserDto> {
        val response = userRemoteDataSource.getOneUser(email)
        return if (response.data != null) {
            ResultRetrofit.Success(response.data)
        } else {
            ResultRetrofit.Error("Wrong")
        }
    }

    suspend fun createUser(userCreateDto: UserCreateDto): ResultRetrofit<UserDto> {
        val response = userRemoteDataSource.createUser(userCreateDto)
        return if (response.data != null) {
            ResultRetrofit.Success(response.data)
        } else {
            ResultRetrofit.Error("Wrong")
        }
    }

    suspend fun checkUser(email: String): ResultRetrofit<Boolean> {
        val response = userRemoteDataSource.checkUser(email)
        return if (response.data != null) {
            ResultRetrofit.Success(response.data)
        } else {
            ResultRetrofit.Error("Wrong")
        }
    }

    suspend fun checkCorrectUser(email: String): Boolean{
        return (userLocalDataSource.getOneUser(email) != null) || (userLocalDataSource.getLengthUser() == 0)
    }

    suspend fun insertUser(email: String) {
        userLocalDataSource.insertUser(email)
    }

    suspend fun deleteUser(email: String) {
        userLocalDataSource.deleteUser(email)
    }


}