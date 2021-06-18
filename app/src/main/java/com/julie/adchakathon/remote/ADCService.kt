package com.julie.adchakathon.remote

import com.julie.adchakathon.data.AddRecordResponse
import com.julie.adchakathon.data.SignInResponse
import com.julie.adchakathon.data.SignUpResponse
import com.julie.adchakathon.data.UploadImageResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface ADCService {

    @POST("v1/auth/signup")
    suspend fun register(
        @Body requestBody: RequestBody
    ): Response<SignUpResponse>

    @POST("v1/auth/login")
    suspend fun login(
        @Body requestBody: RequestBody
    ): Response<SignInResponse>

    @Multipart
    @POST("v1/image/upload")
    suspend fun uploadImage(
        @Header("Authorization") accessToken: String,
        @Part file: MultipartBody.Part,
    ): Response<UploadImageResponse>

    @POST("v1/pests-and-diseases/create")
    suspend fun postNewRecord(
        @Header("Authorization") accessToken: String,
        @Body requestBody: RequestBody
    ): Response<AddRecordResponse>

}