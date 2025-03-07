package com.example.watpato.login.data.datasource

import com.example.watpato.login.data.model.LoginRequest
import com.example.watpato.login.data.model.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("users/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<UserResponse>
}
