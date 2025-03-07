package com.example.watpato.BookPreview.data.model.requests

import kotlinx.serialization.Serializable

@Serializable
data class BookSubscriptionRequest (
    val userId: Int,
    val bookId: Int
)