package com.example.watpato.features.views.profileView.data.model.subscriptions

import com.example.watpato.features.views.profileView.data.model.entities.Book

data class BookSubscription (
    val id: Int,
    val username: String,
    val subscriptions: List<Book>
)