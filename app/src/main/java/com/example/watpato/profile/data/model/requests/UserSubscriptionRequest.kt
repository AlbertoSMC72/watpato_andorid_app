package com.example.watpato.profile.data.model.requests

import kotlinx.serialization.Serializable

@Serializable
data class UserSubscriptionRequest(
    val userId: Int,
    val writerId: Int
)