package com.example.watpato.profile.domain

import com.example.watpato.profile.data.model.subscriptions.UserSubscription
import com.example.watpato.profile.data.repository.ProfileRepository

class UserSubscriptionUseCase {
    private val profileRepository = ProfileRepository()

    suspend fun getUserSubscriptions(userId: Int): Result<UserSubscription> {
        return profileRepository.getUserSubscriptions(userId)
    }
}