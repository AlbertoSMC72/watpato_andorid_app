package com.example.watpato.register.domain

import com.example.watpato.register.data.model.UsernameValidateDTO
import com.example.watpato.register.data.repository.RegisterRepository

class UsernameValidateUseCase {
    private  val repository = RegisterRepository()

    suspend operator fun invoke() : Result<UsernameValidateDTO> {
        val result  = repository.validateUsername()

        // En caso de existir acá debe estar la lógica de negocio
        return result
    }
}