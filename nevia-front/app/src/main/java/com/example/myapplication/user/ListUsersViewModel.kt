package com.example.myapplication.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.repository.UserRepository
import com.example.myapplication.service.dto.UserDto
import kotlinx.coroutines.launch

class ListUsersViewModel(private val repository: UserRepository) :
    ViewModel() {

    private val tag = "CONSOLE"
    private val _listUser = MutableLiveData<List<UserDto>>()
    val users: LiveData<List<UserDto>>
    get() = _listUser

    init {
        loadUsers()
    }

    fun loadUsers() {
        viewModelScope.launch {
            val response = repository.getUsersFromInternet()
            if (response.data != null) {
                _listUser.postValue(response.data!!)
            }
        }
    }

}