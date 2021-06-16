package com.julie.adchakathon.data

data class SignUpRequest(
    val firstName: String,
    val lastName: String,
    val location: String,
    val password: String,
    val phoneNumber: String,
    val username: String
)