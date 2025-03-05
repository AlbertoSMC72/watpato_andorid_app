package com.example.watpato.register.domain

import com.example.watpato.register.data.model.CreateUserRequest
import com.example.watpato.register.data.model.UserDTO
import com.example.watpato.register.data.model.UsernameValidateDTO
import com.example.watpato.register.data.repository.RegisterRepository

class CreateUserUSeCase {
    private  val repository = RegisterRepository()

    suspend operator fun invoke(user: CreateUserRequest) : Result<UserDTO> {
        val result = repository.createUser(user)

        //En caso de existir acá debe estar la lógica de negocio
        return result
    }
}