package com.example.watpato.features.authorization.login.domain


import com.example.watpato.features.authorization.login.data.repository.AuthRepository

class LoginUseCase {
    private val authRepository = AuthRepository()

    suspend operator fun invoke(email: String, password: String): Result<String> {
        return authRepository.login(email, password)
    }
}

