package com.example.myapplication.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.repository.BookingRepository
import com.example.myapplication.repository.UserRepository
import com.example.myapplication.service.dto.BookingDto
import com.example.myapplication.service.dto.UserDto
import kotlinx.coroutines.launch

class ListBookingViewModel(private val userRepository: UserRepository, private val repository: BookingRepository): ViewModel() {

    private val tag = "CONSOLE"
    private val _listBooking = MutableLiveData<List<BookingDto>>()
    val bookings: LiveData<List<BookingDto>>
    get() = _listBooking

    private val _user = MutableLiveData<UserDto>()
    val user: LiveData<UserDto>
        get() = _user

    fun loadUser(email: String) {
        viewModelScope.launch {
            val response = userRepository.getOneUserFromInternet(email)
            if (response.data != null) {
                _user.postValue(response.data!!)
            }
        }
    }

    fun loadListBooking(email: String){
        viewModelScope.launch {
            val response = repository.getRoomForBooking(email)
            if (response.data != null) {
                _listBooking.postValue(response.data!!)
            }
        }
    }
}