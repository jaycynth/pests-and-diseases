package com.julie.adchakathon.repositories

import com.julie.adchakathon.remote.AddRecordRemoteDataSource
import com.julie.adchakathon.utils.getOperation
import okhttp3.RequestBody
import java.io.File
import javax.inject.Inject

class AddRecordRepo @Inject
constructor(private val addRecordRemoteDataSource: AddRecordRemoteDataSource) {

    fun uploadImage(accessToken: String, photo: File) = getOperation(
        networkCall = { addRecordRemoteDataSource.uploadImage(accessToken, photo) },
    )

    fun addRecord(accessToken: String, requestBody: RequestBody) = getOperation(
        networkCall = { addRecordRemoteDataSource.addRecord(accessToken, requestBody) }
    )

}