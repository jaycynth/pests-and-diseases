package com.julie.adchakathon.ui.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.julie.adchakathon.repositories.HomeRepo

class HomeViewModel @ViewModelInject constructor(private val homeRepo: HomeRepo) : ViewModel() {

}

