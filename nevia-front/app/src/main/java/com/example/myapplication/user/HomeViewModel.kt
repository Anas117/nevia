package com.example.myapplication.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.repository.NotificationRepository
import com.example.myapplication.repository.UserRepository
import com.example.myapplication.service.dto.UserDto
import com.example.myapplication.service.dto.firebase.RegistrationDto
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: UserRepository,
    private val notificationRepository: NotificationRepository
) :
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

    fun sendRegistration(registrationDto: RegistrationDto){
        viewModelScope.launch {
            // send registration firebase
            notificationRepository.registration(registrationDto)
        }
    }

}