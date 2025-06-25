package com.example.myapplication.dao.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(
    @PrimaryKey(autoGenerate = true) var idUser: Int = 0,
    val email: String
) {
}