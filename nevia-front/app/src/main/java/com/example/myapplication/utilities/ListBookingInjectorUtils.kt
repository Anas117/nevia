package com.example.myapplication.utilities

import android.content.Context
import com.example.myapplication.dao.UserLocalDataSource
import com.example.myapplication.dao.database.ProjectDatabase
import com.example.myapplication.repository.BookingRepository
import com.example.myapplication.repository.UserRepository
import com.example.myapplication.service.booking.BookingRemoteDataSource
import com.example.myapplication.service.user.ApiUser
import com.example.myapplication.service.user.UserRemoteDataSource
import com.example.myapplication.user.factory.ListBookingViewModelFactory

object ListBookingInjectorUtils {
    private var bookingRemoteDataSource: BookingRemoteDataSource? = null
    private var userRemoteDataSource: UserRemoteDataSource? = null

    private var userLocalDataSource: UserLocalDataSource? = null

    private var userRepository: UserRepository? = null
    private var bookingRepository: BookingRepository? = null

    private var listBookingViewModelFactory: ListBookingViewModelFactory? = null
    
    private fun createUserRemoteDataSource(): UserRemoteDataSource {
        val dataSource = UserRemoteDataSource(ApiUser)
        userRemoteDataSource = dataSource
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
            UserRepository(
                provideUserDataSource(),
                provideLocalDataSource(context)
            )
        userRepository = repository
        return repository
    }

    private fun createBookingRepository(): BookingRepository{
        val repository = BookingRepository(provideBookingDataSource())
        bookingRepository = repository
        return repository
    }

    private fun createFactory(context: Context): ListBookingViewModelFactory {
        val factory = ListBookingViewModelFactory(providerUserRepository(context), providerBookingRepository())
        listBookingViewModelFactory = factory
        return factory
    }

    private fun provideUserDataSource() = userRemoteDataSource
        ?: createUserRemoteDataSource()

    private fun provideBookingDataSource() = bookingRemoteDataSource
        ?: createBookingRemoteDataSource()

    private fun provideLocalDataSource(context: Context) =
        userLocalDataSource
            ?: createUserLocalDataSource(context)

    private fun providerUserRepository(context: Context) =
        userRepository ?: createUserRepository(context)

    private fun providerBookingRepository() =
        bookingRepository ?: createBookingRepository()

    // factory
    fun provideListBookingViewModelFactory(context: Context) =
        listBookingViewModelFactory ?: createFactory(context)
}
