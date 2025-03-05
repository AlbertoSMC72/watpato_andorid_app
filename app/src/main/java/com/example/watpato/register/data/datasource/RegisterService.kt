package com.example.watpato.register.data.datasource

import com.example.watpato.register.data.model.CreateUserRequest
import com.example.watpato.register.data.model.UserDTO
import com.example.watpato.register.data.model.UsernameValidateDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface RegisterService {

    @GET("v3/9f81fd5c-05d2-4e1b-bae6-d3bd4eec084b")
    suspend fun validateUsername() : Response<UsernameValidateDTO>

    @POST("app/user")
    suspend fun createUser(@Body request: CreateUserRequest): Response<UserDTO>
    abstract fun login(mapOf: Map<String, String>): Any

}