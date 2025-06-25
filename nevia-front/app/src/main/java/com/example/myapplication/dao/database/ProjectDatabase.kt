package com.example.myapplication.dao.database;

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.myapplication.dao.UserDao
import com.example.myapplication.dao.database.entity.User

@Database(
    entities = [User::class], version = 1
)
abstract class ProjectDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {
        private var instance: ProjectDatabase? = null

        @Synchronized
        fun getInstance(ctx: Context): ProjectDatabase {
            if (instance == null)
                instance = Room.databaseBuilder(
                    ctx.applicationContext, ProjectDatabase::class.java,
                    "note_database"
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build()

            return instance!!

        }

        private val roomCallback = object : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
            }
        }
    }
}