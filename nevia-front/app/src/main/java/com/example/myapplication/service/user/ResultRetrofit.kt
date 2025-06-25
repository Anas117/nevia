package com.example.myapplication.service.user

sealed class ResultRetrofit<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T) : ResultRetrofit<T>(data)
    class Error<T>(message: String?, data: T? = null) : ResultRetrofit<T>(data, message)
}
