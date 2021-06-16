package com.julie.adchakathon.remote

import com.julie.adchakathon.data.SignInResponse
import com.julie.adchakathon.data.SignUpResponse
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ADCService {

    @POST("v1/auth/signup")
    suspend fun register(
        @Body requestBody: RequestBody
    ): Response<SignUpResponse>

    @POST("v1/auth/login")
    suspend fun login(
        @Body requestBody: RequestBody
    ): Response<SignInResponse>

    @GET("")
    suspend fun getTypeResponse()

    @POST("")
    suspend fun postNewType()

}