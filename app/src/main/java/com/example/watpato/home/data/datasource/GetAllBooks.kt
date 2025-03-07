package com.example.watpato.home.data.datasource

import com.example.watpato.home.data.model.Book
import retrofit2.Response
import retrofit2.http.GET

interface GetAllBooks {
    @GET("books")
    suspend fun getAllBooks(): Response<List<Book>>
}