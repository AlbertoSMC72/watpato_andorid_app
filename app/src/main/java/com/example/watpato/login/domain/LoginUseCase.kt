package com.example.watpato.login.domain

import com.example.watpato.login.data.repository.AuthRepository

class LoginUseCase {
    private val authRepository = AuthRepository()

    suspend operator fun invoke(username: String, password: String): Result<String> {
        return authRepository.login(username, password)
    }
}

