package com.julie.adchakathon.ui.addRecord

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.julie.adchakathon.repositories.AddRecordRepo
import okhttp3.RequestBody
import java.io.File

class AddRecordViewModel
@ViewModelInject constructor(private val addRecordRepo: AddRecordRepo) : ViewModel(){

    fun uploadImage(accessToken:String, photo:File) =
            addRecordRepo.uploadImage(accessToken, photo)


    fun addRecord(accessToken: String, requestBody: RequestBody) =
        addRecordRepo.addRecord(accessToken, requestBody)

}