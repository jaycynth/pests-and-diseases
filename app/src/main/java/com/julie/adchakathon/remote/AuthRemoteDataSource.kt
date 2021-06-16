package com.julie.adchakathon.remote

import okhttp3.RequestBody
import javax.inject.Inject


class AuthRemoteDataSource @Inject constructor(
    private val adcService: ADCService
) : BaseDataSource() {

    suspend fun register(requestBody: RequestBody) = getResult {
        adcService.register(requestBody)
    }

    suspend fun login(requestBody: RequestBody) = getResult {
        adcService.login(requestBody)
    }
}