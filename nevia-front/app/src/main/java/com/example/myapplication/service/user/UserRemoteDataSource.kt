package com.example.myapplication.service.user


import com.example.myapplication.service.dto.UserCreateDto
import com.example.myapplication.service.dto.UserDto

class UserRemoteDataSource(apiUser: ApiUser) : UserDataSource {

    private val service = ApiUser.build()

    override suspend fun getUsers(): ResultRetrofit<List<UserDto>> {
        val response = service.getUsers()
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

    override suspend fun getOneUser(email: String): ResultRetrofit<UserDto> {
        val response = service.getOneUser(email)
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

    override suspend fun createUser(userCreateDto: UserCreateDto): ResultRetrofit<UserDto> {
        val response = service.createUser(userCreateDto)
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

    override suspend fun checkUser(email: String): ResultRetrofit<Boolean> {
        val response = service.checkUser(email)
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
