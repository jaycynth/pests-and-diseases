package com.julie.adchakathon.remote

import android.util.Log
import com.julie.adchakathon.utils.Resource
import retrofit2.Response
import java.io.BufferedReader
import java.io.InputStreamReader

abstract class BaseDataSource {

    protected suspend fun <T> getResult(call: suspend () -> Response<T>): Resource<T> {
        try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) return Resource.success(body)
            }

            val error = StringBuilder()
            try {
                var bufferedReader: BufferedReader? = null
                if (response.errorBody() != null) {
                    bufferedReader = BufferedReader(
                        InputStreamReader(
                            response.errorBody()!!.byteStream()
                        )
                    )
                    var eLine: String? = null
                    while (bufferedReader.readLine().also { eLine = it } != null) {
                        error.append(eLine)
                    }
                    bufferedReader.close()
                }
            } catch (e: java.lang.Exception) {
                error.append(e.message)
            }
            Log.e("Error", error.toString())


            return error(error.toString())

        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }

    private fun <T> error(message: String, data: T? = null): Resource<T> {
        Log.d("MESSAGE", message)
        return Resource.error("An error occurred: $message", data)
    }

}
