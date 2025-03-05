package com.example.watpato.login.data.repository

import com.example.watpato.core.network.RetrofitHelper
import com.example.watpato.login.data.datasource.AuthService

class AuthRepository {
    private val authService = RetrofitHelper.createService(AuthService::class.java)

    suspend fun login(username: String, password: String): Result<String> {
        return try {
            val response = authService.login(
                mapOf(
                    "nombre_usuario" to username,
                    "contrasena" to password
                )
            )
            if (response.isSuccessful) {
                val token = response.body()?.get("token")
                if (token != null) {
                    Result.success(token)
                } else {
                    Result.failure(Exception("Token no recibido"))
                }
            } else {
                Result.failure(Exception("Error en la autenticación"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
