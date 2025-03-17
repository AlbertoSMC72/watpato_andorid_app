package com.example.watpato.features.views.profileView.domain

import com.example.watpato.features.views.profileView.data.model.subscriptions.BookSubscription
import com.example.watpato.features.views.profileView.data.repository.ProfileRepository

class BookSubscriptionUseCase {
    private val profileRepository = ProfileRepository()

    suspend fun getBookSubscriptions(userId: Int): Result<BookSubscription> {
        return profileRepository.getBookSubscriptions(userId)
    }
}