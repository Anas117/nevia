package com.example.myapplication.dao;

import android.util.Log
import com.example.myapplication.dao.database.ProjectDatabase
import com.example.myapplication.dao.database.entity.User

class UserLocalDataSource(db: ProjectDatabase) {

    private val tag = "CONSOLE"
    private val userDao: UserDao = db.userDao()

    suspend fun getOneUser(email: String): User {
        Log.v(tag, "get one user")
        return userDao.getOneUser(email)
    }

    suspend fun getLengthUser(): Int{
        return userDao.getLengthUser()
    }

    suspend fun insertUser(email: String) {
        userDao.insert(User(1,email))
    }

    suspend fun deleteUser(email: String){
        userDao.delete(User(1,email))
    }

}