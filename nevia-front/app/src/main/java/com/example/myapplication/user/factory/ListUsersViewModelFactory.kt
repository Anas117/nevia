package com.example.myapplication.user.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.repository.UserRepository
import com.example.myapplication.user.ListUsersViewModel

class ListUsersViewModelFactory(
    private val userRepository: UserRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ListUsersViewModel(userRepository) as T
    }
}