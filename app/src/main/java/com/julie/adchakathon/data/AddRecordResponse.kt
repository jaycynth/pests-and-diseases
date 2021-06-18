package com.julie.adchakathon.data

data class AddRecordResponse(
    val code: Int,
    val `data`: Data,
    val message: String,
    val timestamp: Int
)