package com.example.watpato.profile.domain

import com.example.watpato.profile.data.model.subscriptions.UserSubscription
import com.example.watpato.profile.data.repository.ProfileRepository

class UserSubscriptionUseCase {
    private val profileRepository = ProfileRepository()

    suspend fun subscribe(userId: Int, writerId: Int): Result<Unit> {
        return profileRepository.subscribeToUser(userId, writerId)
    }

    suspend fun unsubscribe(userId: Int, writerId: Int): Result<Unit> {
        return profileRepository.unsubscribeToUser(userId, writerId)
    }

    suspend fun getUserSubscriptions(userId: Int): Result<UserSubscription> {
        return profileRepository.getUserSubscriptions(userId)
    }
}