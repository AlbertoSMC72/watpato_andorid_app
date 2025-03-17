package com.example.watpato.features.authorization.register.data.model

data class CreateUserRequest(
    val username: String,
    val email: String,
    val password: String,
    val firebaseToken: String?
)