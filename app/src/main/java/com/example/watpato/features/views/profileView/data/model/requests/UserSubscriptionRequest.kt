package com.example.watpato.features.views.profileView.data.model.requests

import kotlinx.serialization.Serializable

@Serializable
data class UserSubscriptionRequest(
    val userId: Int,
    val writerId: Int
)