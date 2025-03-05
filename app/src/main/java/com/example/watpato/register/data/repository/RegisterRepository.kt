package com.example.watpato.register.data.repository

import com.example.watpato.core.network.RetrofitHelper
import com.example.watpato.register.data.model.CreateUserRequest
import com.example.watpato.register.data.model.UserDTO
import com.example.watpato.register.data.model.UsernameValidateDTO


class RegisterRepository()  {
    private val registerService = RetrofitHelper.registerService

    suspend fun validateUsername() : Result<UsernameValidateDTO>  {
        return try {
            
            val response = registerService.validateUsername()

            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(response.errorBody()?.string()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun createUser(request: CreateUserRequest): Result<UserDTO> {
        return try {
            val response = registerService.createUser(request)
            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(response.errorBody()?.string()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}