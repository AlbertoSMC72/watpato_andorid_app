package com.example.watpato.profile.data.model.subscriptions

import com.example.watpato.profile.data.model.entities.User

data class UserSubscription (
    val id: Int,
    val username: String,
    val subscriptions: List<User>
)