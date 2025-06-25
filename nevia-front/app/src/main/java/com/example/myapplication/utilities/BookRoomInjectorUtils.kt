package com.example.myapplication.utilities

import android.content.Context
import com.example.myapplication.dao.UserLocalDataSource
import com.example.myapplication.dao.database.ProjectDatabase
import com.example.myapplication.repository.BookingRepository
import com.example.myapplication.repository.RoomRepository
import com.example.myapplication.repository.UserRepository
import com.example.myapplication.service.booking.BookingRemoteDataSource
import com.example.myapplication.service.room.RoomRemoteDataSource
import com.example.myapplication.service.user.ApiUser
import com.example.myapplication.service.user.UserRemoteDataSource
import com.example.myapplication.user.factory.BookRoomViewModelFactory

object BookRoomInjectorUtils {
    private var userRemoteDataSource: UserRemoteDataSource? = null
    private var roomRemoteDataSource: RoomRemoteDataSource? = null
    private var bookingRemoteDataSource: BookingRemoteDataSource? = null

    private var userLocalDataSource: UserLocalDataSource? = null

    private var userRepository: UserRepository? = null
    private var roomRepository: RoomRepository? = null
    private var bookingRepository: BookingRepository? = null

    private var bookRoomViewModelFactory: BookRoomViewModelFactory? = null

    private fun createUserRemoteDataSource(): UserRemoteDataSource {
        val dataSource = UserRemoteDataSource(ApiUser)
        userRemoteDataSource = dataSource
        return dataSource
    }

    private fun createRoomRemoteDataSource(): RoomRemoteDataSource {
        val dataSource = RoomRemoteDataSource()
        roomRemoteDataSource = dataSource
        return dataSource
    }

    private fun createBookingRemoteDataSource(): BookingRemoteDataSource {
        val dataSource = BookingRemoteDataSource()
        bookingRemoteDataSource = dataSource
        return dataSource
    }

    private fun createUserLocalDataSource(context: Context): UserLocalDataSource {
        val db = ProjectDatabase.getInstance(context)
        val dataSource = UserLocalDataSource(db)
        userLocalDataSource = dataSource
        return dataSource
    }

    private fun createUserRepository(context: Context): UserRepository {
        val repository =
            UserRepository(provideUserDataSource(), provideLocalDataSource(context))
        userRepository = repository
        return repository
    }

    private fun createRoomRepository(): RoomRepository {
        val repository =
            RoomRepository(provideRoomDataSource())
        roomRepository = repository
        return repository
    }

    private fun createBookingRepository(): BookingRepository{
        val repository = BookingRepository(provideBookingDataSource())
        bookingRepository = repository
        return repository
    }

    private fun createFactory(context: Context): BookRoomViewModelFactory {
        val factory = BookRoomViewModelFactory(providerUserRepository(context), providerRoomRepository(), providerBookingRepository())
        bookRoomViewModelFactory = factory
        return factory
    }
    // remotedatasource
    private fun provideUserDataSource() = userRemoteDataSource ?: createUserRemoteDataSource()
    private fun provideRoomDataSource() = roomRemoteDataSource ?: createRoomRemoteDataSource()
    private fun provideBookingDataSource() = bookingRemoteDataSource ?: createBookingRemoteDataSource()

    // localdatasource
    private fun provideLocalDataSource(context: Context) =
        userLocalDataSource ?: createUserLocalDataSource(context)

    //repository
    private fun providerUserRepository(context: Context) =
        userRepository ?: createUserRepository(context)

    private fun providerRoomRepository() =
        roomRepository ?: createRoomRepository()

    private fun providerBookingRepository() =
        bookingRepository ?: createBookingRepository()

    // factory
    fun provideBookRoomViewModelFactory(context: Context) =
        bookRoomViewModelFactory ?: createFactory(context)
    

}