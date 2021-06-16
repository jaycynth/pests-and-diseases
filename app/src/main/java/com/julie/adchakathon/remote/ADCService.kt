package com.julie.adchakathon.remote

import retrofit2.http.GET
import retrofit2.http.POST

interface ADCService {

    @POST("")
    suspend fun register()

    @POST("")
    suspend fun login()

    @GET("")
    suspend fun getTypeResponse()

    @POST("")
    suspend fun postNewType()

}