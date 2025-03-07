package com.example.watpato.profile.domain


import com.example.watpato.profile.data.model.entities.UserProfile
import com.example.watpato.profile.data.repository.ProfileRepository

class ProfileUseCase {
    private val profileRepository = ProfileRepository()

    suspend fun getBooksByAuthor(authorId: Int): Result<UserProfile> {
        return profileRepository.getBooksByAuthor(authorId)
    }
}