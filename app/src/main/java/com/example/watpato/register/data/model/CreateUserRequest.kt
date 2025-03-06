package com.example.watpato.register.data.model

data class CreateUserRequest(
    val username: String,
    val email: String,
    val password: String,
    val firebaseToken: String?
)