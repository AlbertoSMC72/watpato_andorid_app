package com.example.watpato.profile.data.model.subscriptions

import com.example.watpato.profile.data.model.entities.Book

data class BookSubscription (
    val id: Int,
    val username: String,
    val subscriptions: List<Book>
)