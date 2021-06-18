package com.julie.adchakathon.data

data class AddRecordRequest(
    val causes: String,
    val description: String,
    val image: String,
    val name: String,
    val plantId: Int,
    val prevention: String,
    val signSymptoms: List<String>,
    val type: String
)