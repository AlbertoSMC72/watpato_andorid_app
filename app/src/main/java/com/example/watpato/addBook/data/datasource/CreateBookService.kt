package com.example.watpato.addBook.data.datasource

import com.example.watpato.addBook.data.model.BookRequest
import com.example.watpato.addBook.data.model.GenreDTO
import com.example.watpato.addBook.data.model.InsertedIdResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface CreateBookService {

    @GET("genres/all")
    suspend fun getAllGenres(): Response<List<GenreDTO>>

    @POST("genres/")
    suspend fun createGenre(@Body newGenre: Map<String, String>): Response<List<InsertedIdResponse>>

    @POST("books/")
    suspend fun createBook(@Body bookRequest: BookRequest): Response<Unit>
}