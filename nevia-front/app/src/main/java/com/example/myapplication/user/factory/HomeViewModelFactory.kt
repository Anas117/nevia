package com.example.myapplication.user.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.repository.NotificationRepository
import com.example.myapplication.repository.UserRepository
import com.example.myapplication.user.HomeViewModel

class HomeViewModelFactory(
    private val userRepository: UserRepository,
    private val notificationRepository: NotificationRepository
) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(userRepository, notificationRepository) as T
    }
}