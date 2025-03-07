package com.example.watpato.home.data.model

import com.google.gson.annotations.SerializedName

data class Book(
    val id: Int,
    val title: String,
    val description: String,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("author_id") val authorId: Int,
    val genres: List<String>
)