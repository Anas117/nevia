package com.example.myapplication.utilities;

import android.content.Context
import com.example.myapplication.dao.UserLocalDataSource
import com.example.myapplication.dao.database.ProjectDatabase
import com.example.myapplication.repository.UserRepository
import com.example.myapplication.service.user.ApiUser
import com.example.myapplication.service.user.UserRemoteDataSource
import com.example.myapplication.user.factory.ListUsersViewModelFactory

object ListUsersInjectorUtils {
    private var userRemoteDataSource: UserRemoteDataSource? = null
    private var userLocalDataSource: UserLocalDataSource? = null
    private var userRepository: UserRepository? = null
    private var listUsersViewModelFactory: ListUsersViewModelFactory? = null

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
            UserRepository(provideRemoteDataSource(), provideLocalDataSource(context))
        userRepository = repository
        return repository
    }

    private fun createFactory(context: Context): ListUsersViewModelFactory {
        val factory = ListUsersViewModelFactory(providerRepository(context))
        listUsersViewModelFactory = factory
        return factory
    }

    private fun provideRemoteDataSource() = userRemoteDataSource ?: createUserRemoteDataSource()
    private fun provideLocalDataSource(context: Context) =
        userLocalDataSource ?: createUserLocalDataSource(context)

    private fun providerRepository(context: Context) =
        userRepository ?: createUserRepository(context)

    fun provideUserViewModelFactory(context: Context) =
        listUsersViewModelFactory ?: createFactory(context)

}
