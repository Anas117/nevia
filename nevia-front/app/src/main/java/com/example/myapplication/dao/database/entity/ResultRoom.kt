package com.example.myapplication.dao.database.entity

sealed class ResultRoom<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T) : ResultRoom<T>(data)
    class Error<T>(message: String?, data: T? = null) : ResultRoom<T>(data, message)
}
