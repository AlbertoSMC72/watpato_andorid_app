package com.example.watpato.BookPreview.data.datasource

import com.example.watpato.BookPreview.data.model.entities.Book
import com.example.watpato.BookPreview.data.model.requests.BookSubscriptionRequest

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface GetBook {
    @GET("books/{id}")
    suspend fun getBookById(@Path("id") bookId: Int): Response<Book>

    @POST("book/subscribe")
    suspend fun subscribeToBook(@Body request: BookSubscriptionRequest)

    @POST("book/unsubscribe")
    suspend fun unsubscribeToBook(@Body request: BookSubscriptionRequest)
}