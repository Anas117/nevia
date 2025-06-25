package com.example.myapplication.user.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.repository.BookingRepository
import com.example.myapplication.repository.RoomRepository
import com.example.myapplication.repository.UserRepository
import com.example.myapplication.user.BookRoomViewModel
import com.example.myapplication.user.ListBookingViewModel

class ListBookingViewModelFactory (
    private val userRepository: UserRepository,
    private val bookingRepository: BookingRepository
) : ViewModelProvider.NewInstanceFactory(){
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ListBookingViewModel(userRepository, bookingRepository) as T
    }
}