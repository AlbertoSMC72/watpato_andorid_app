package com.example.watpato.features.home.data.datasource

import com.example.watpato.features.home.data.model.Book
import retrofit2.Response
import retrofit2.http.GET

interface GetAllBooks {
    @GET("books")
    suspend fun getAllBooks(): Response<List<Book>>
}