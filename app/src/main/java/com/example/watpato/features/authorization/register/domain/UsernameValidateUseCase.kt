package com.example.watpato.features.authorization.register.domain

import com.example.watpato.features.authorization.register.data.model.UsernameValidateDTO
import com.example.watpato.features.authorization.register.data.repository.RegisterRepository

class UsernameValidateUseCase {
    private  val repository = RegisterRepository()

    suspend operator fun invoke() : Result<UsernameValidateDTO> {
        val result  = repository.validateUsername()

        // En caso de existir acá debe estar la lógica de negocio
        return result
    }
}