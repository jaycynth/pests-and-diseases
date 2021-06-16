package com.julie.adchakathon.repositories

import com.julie.adchakathon.remote.AuthRemoteDataSource
import com.julie.adchakathon.utils.getOperation
import okhttp3.RequestBody
import javax.inject.Inject

class AuthRepo @Inject constructor(private val authRemoteDataSource: AuthRemoteDataSource){

    fun login(requestBody: RequestBody) = getOperation(
        networkCall = { authRemoteDataSource.login(requestBody) },
    )

    fun register(requestBody: RequestBody) = getOperation(
        networkCall = { authRemoteDataSource.register(requestBody) },
    )
}