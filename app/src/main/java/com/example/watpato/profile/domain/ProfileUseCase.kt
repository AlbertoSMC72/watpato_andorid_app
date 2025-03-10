package com.example.watpato.profile.domain


import com.example.watpato.profile.data.model.entities.UserProfile
import com.example.watpato.profile.data.repository.ProfileRepository

class ProfileUseCase {
    private val profileRepository = ProfileRepository()

    suspend fun getBooksByAuthor(authorId: Int): Result<UserProfile> {
        return profileRepository.getBooksByAuthor(authorId)
    }

    suspend fun subscribe(userId: Int, writerId: Int): Result<Unit> {
        return profileRepository.subscribeToUser(userId, writerId)
    }

    suspend fun unsubscribe(userId: Int, writerId: Int): Result<Unit> {
        return profileRepository.unsubscribeToUser(userId, writerId)
    }

    suspend fun isSubscribed(userId: Int, writerId: Int): Result<Boolean> {
        return profileRepository.isSubscribedToUser(userId, writerId)
    }
}