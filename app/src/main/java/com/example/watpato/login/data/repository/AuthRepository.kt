package com.example.watpato.login.data.repository

import com.example.watpato.core.data.UserInfoProvider
import com.example.watpato.core.network.RetrofitHelper
import com.example.watpato.login.data.datasource.AuthService
import com.example.watpato.login.data.model.LoginRequest
import com.example.watpato.login.data.model.UserResponse

class AuthRepository {
    private val authService = RetrofitHelper.createService(AuthService::class.java)

    suspend fun login(email: String, password: String): Result<String> {
        return try {

            val response = authService.login(LoginRequest(email, password))

            if (response.isSuccessful) {
                val body: UserResponse? = response.body()
                if (body != null) {
                    // Guardamos en UserInfoProvider
                    UserInfoProvider.userID = body.id
                    UserInfoProvider.username = body.username
                    Result.success("Inicio de sesión exitoso")
                } else {
                    Result.failure(Exception("El cuerpo de la respuesta está vacío"))
                }
            } else {
                Result.failure(Exception("Error en la autenticación: ${response.errorBody()?.string()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
