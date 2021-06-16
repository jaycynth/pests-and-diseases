package com.julie.adchakathon.ui.auth

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.julie.adchakathon.repositories.AuthRepo

class AuthViewModel @ViewModelInject constructor(val authRepo: AuthRepo): ViewModel() {
}