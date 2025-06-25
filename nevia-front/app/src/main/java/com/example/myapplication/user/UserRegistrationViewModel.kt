package com.example.myapplication.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.repository.UserRepository
import com.example.myapplication.service.dto.UserCreateDto
import kotlinx.coroutines.launch

class UserRegistrationViewModel (private val repository: UserRepository) :
    ViewModel() {

    private val tag = "CONSOLE"

    private val _checkUser = MutableLiveData<Boolean>()
    val checkUser: LiveData<Boolean>
    get() = _checkUser

    fun checkUser(email : String) {
        viewModelScope.launch {
            val response = repository.checkUser(email)
            if (response.data != null) {
                _checkUser.postValue(response.data!!)
            }
        }
    }

    fun createUser(userCreateDto: UserCreateDto){
        viewModelScope.launch {
            repository.createUser(userCreateDto)
            repository.insertUser(userCreateDto.email)
        }
    }


}