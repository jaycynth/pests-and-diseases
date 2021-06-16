package com.julie.adchakathon.ui.auth

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.julie.adchakathon.repositories.AuthRepo
import kotlinx.coroutines.launch
import okhttp3.RequestBody

class AuthViewModel @ViewModelInject constructor(private val authRepo: AuthRepo) : ViewModel() {

    fun login(requestBody: RequestBody) =
        authRepo.login(requestBody)

    fun register(requestBody: RequestBody) =
        authRepo.register(requestBody)
}