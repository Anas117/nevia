package com.example.myapplication.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.repository.UserRepository
import kotlinx.coroutines.launch

class MainPageViewModel (private val repository: UserRepository) :
    ViewModel() {

    private val tag = "CONSOLE"

    private val _checkUser = MutableLiveData<Boolean>()
    val checkUser: LiveData<Boolean>
    get() = _checkUser

    fun checkUser(email : String) {
        viewModelScope.launch {
            // check if user exist in back
            val response = repository.checkUser(email)
            if (response.data != null) {

                //check if it is the correct user on the device
                if(repository.checkCorrectUser(email)) _checkUser.postValue(response.data!!)
            }
        }
    }
}