package com.example.watpato.features.views.profileView.domain

import com.example.watpato.features.views.profileView.data.model.subscriptions.UserSubscription
import com.example.watpato.features.views.profileView.data.repository.ProfileRepository

class UserSubscriptionUseCase {
    private val profileRepository = ProfileRepository()

    suspend fun getUserSubscriptions(userId: Int): Result<UserSubscription> {
        return profileRepository.getUserSubscriptions(userId)
    }
}