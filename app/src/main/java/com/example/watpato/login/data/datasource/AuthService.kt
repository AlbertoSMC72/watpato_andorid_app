package com.example.watpato.login.data.datasource

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("users/login")
    suspend fun login(@Body credentials: Map<String, String>): Response<Map<String, String>>
}
