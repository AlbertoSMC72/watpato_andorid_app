package com.example.watpato.features.views.profileView.data.model.subscriptions

import com.example.watpato.features.views.profileView.data.model.entities.User

data class UserSubscription (
    val id: Int,
    val username: String,
    val subscriptions: List<User>
)