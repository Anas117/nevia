package com.example.myapplication.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.repository.UserRepository
import com.example.myapplication.service.dto.UserDto
import kotlinx.coroutines.launch

class UserViewModel(private val repository: UserRepository) :
    ViewModel() {

    private val tag = "CONSOLE"
    private val _user = MutableLiveData<UserDto>()
    val user: LiveData<UserDto>
    get() = _user

    fun loadUser(email: String) {
        viewModelScope.launch {
            val response = repository.getOneUserFromInternet(email)
            if (response.data != null) {
                _user.postValue(response.data!!)
            }
        }
    }
}