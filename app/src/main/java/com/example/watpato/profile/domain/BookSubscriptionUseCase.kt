package com.example.watpato.profile.domain

import com.example.watpato.profile.data.model.subscriptions.BookSubscription
import com.example.watpato.profile.data.repository.ProfileRepository

class BookSubscriptionUseCase {
    private val profileRepository = ProfileRepository()

    suspend fun getBookSubscriptions(userId: Int): Result<BookSubscription> {
        return profileRepository.getBookSubscriptions(userId)
    }
}