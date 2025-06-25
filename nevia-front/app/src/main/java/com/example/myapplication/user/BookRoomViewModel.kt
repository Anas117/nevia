package com.example.myapplication.user

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.repository.BookingRepository
import com.example.myapplication.repository.RoomRepository
import com.example.myapplication.repository.UserRepository
import com.example.myapplication.service.dto.BookingDto
import com.example.myapplication.service.dto.ChoiceDto
import com.example.myapplication.service.dto.SearchRoomDto
import com.example.myapplication.service.dto.UserDto
import com.example.myapplication.utilities.convertDateAndTimeToString
import kotlinx.coroutines.launch

class BookRoomViewModel(
    private val userRepository: UserRepository,
    private val roomRepository: RoomRepository,
    private val bookingRepository: BookingRepository
) :
    ViewModel() {

    private val tag = "CONSOLE"

    private val _user = MutableLiveData<UserDto>()
    val user: LiveData<UserDto>
        get() = _user

    private val _choice = MutableLiveData<ChoiceDto>()
    val choice: LiveData<ChoiceDto>
        get() = _choice

    private val _listRoom = MutableLiveData<List<SearchRoomDto>>()
    val rooms: LiveData<List<SearchRoomDto>>
        get() = _listRoom

    fun loadUser(email: String) {
        viewModelScope.launch {
            val response = userRepository.getOneUserFromInternet(email)
            if (response.data != null) {
                _user.postValue(response.data!!)
            }
        }
    }

    fun searchRoom(dateSelected: String, startTimeSelected: String, endTimeSelected: String) {
        val startLocalDateTime = convertDateAndTimeToString(dateSelected, startTimeSelected)
        val endLocalDateTime = convertDateAndTimeToString(dateSelected, endTimeSelected)

        Log.i(tag, startLocalDateTime)
        Log.i(tag, endLocalDateTime)

        viewModelScope.launch {
            val response = roomRepository.getRoomForBooking(startLocalDateTime, endLocalDateTime)
            if (response.data != null) {
                _listRoom.postValue(response.data.content)
            }
        }
    }

    fun createBooking(bookingDto: BookingDto) {
        viewModelScope.launch {
            bookingRepository.createBooking(bookingDto)
        }
    }



    fun loadChoice(choiceDto: ChoiceDto) {
        _choice.postValue(choiceDto)
    }
}