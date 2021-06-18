package com.julie.adchakathon.remote

import okhttp3.MediaType
import okhttp3.MediaType.Companion.get
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject


class AddRecordRemoteDataSource @Inject constructor(
        private val adcService: ADCService
) : BaseDataSource() {


    suspend fun uploadImage(accessToken: String, photo: File) = getResult {
        val mFile: RequestBody = photo.asRequestBody("image/*".toMediaTypeOrNull())
        val fileToUpload: MultipartBody.Part = MultipartBody.Part.createFormData("image", "image.jpg", mFile)
        val filename: RequestBody = photo.name.toRequestBody("text/plain".toMediaTypeOrNull())
        adcService.uploadImage(accessToken, fileToUpload)
    }


    suspend fun addRecord(accessToken: String, requestBody: RequestBody) =  getResult {
        adcService.postNewRecord(accessToken, requestBody)
    }


}