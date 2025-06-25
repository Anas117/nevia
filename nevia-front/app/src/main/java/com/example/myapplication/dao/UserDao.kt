package com.example.myapplication.dao;

import androidx.room.*
import com.example.myapplication.dao.database.entity.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    // long : return row id as long value
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User)

    //Int : number row modified
    @Update
    suspend fun updateUser(user: User): Int

    @Delete
    suspend fun delete(user: User): Int

    @Query("delete from User")
    suspend fun deleteAllUsers(): Int

    @Query("select * from User where email == :email")
    suspend fun getOneUser(email: String): User

    @Query("select count(*) from User")
    suspend fun getLengthUser(): Int
}