package com.example.myapplication.utilities

import android.content.Context
import com.example.myapplication.dao.UserLocalDataSource
import com.example.myapplication.dao.database.ProjectDatabase
import com.example.myapplication.repository.UserRepository
import com.example.myapplication.service.user.ApiUser
import com.example.myapplication.service.user.UserRemoteDataSource
import com.example.myapplication.user.factory.UserRegistrationViewModelFactory
import com.example.myapplication.user.factory.UserViewModelFactory

object UserRegistrationInjectorUtils {
    private var userRemoteDataSource: UserRemoteDataSource? = null
    private var userLocalDataSource: UserLocalDataSource? = null
    private var userRepository: UserRepository? = null
    private var userRegistrationViewModelFactory: UserRegistrationViewModelFactory? = null

    private fun createUserRemoteDataSource(): UserRemoteDataSource {
        val dataSource = UserRemoteDataSource(ApiUser)
        userRemoteDataSource = dataSource
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
            UserRepository(provideDataSource(), provideLocalDataSource(context))
        userRepository = repository
        return repository
    }

    private fun createFactory(context: Context): UserRegistrationViewModelFactory {
        val factory = UserRegistrationViewModelFactory(providerRepository(context))
        userRegistrationViewModelFactory = factory
        return factory
    }

    private fun provideDataSource() = userRemoteDataSource ?: createUserRemoteDataSource()
    private fun provideLocalDataSource(context: Context) =
        userLocalDataSource ?: createUserLocalDataSource(context)

    private fun providerRepository(context: Context) =
        userRepository ?: createUserRepository(context)

    fun provideUserRegistrationViewModelFactory(context: Context) =
        userRegistrationViewModelFactory ?: createFactory(context)


}