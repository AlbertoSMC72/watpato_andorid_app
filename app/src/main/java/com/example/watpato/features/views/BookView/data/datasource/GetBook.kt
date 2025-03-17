package com.example.watpato.features.views.BookView.data.datasource

import com.example.watpato.features.views.BookView.data.model.entities.Book
import com.example.watpato.features.views.BookView.data.model.requests.BookSubscriptionRequest
import com.example.watpato.features.views.BookView.data.model.response.SubscriptionResponse

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface GetBook {
    @GET("books/{id}")
    suspend fun getBookById(@Path("id") bookId: Int): Response<Book>

    @POST("bookSub/book/subscribe")
    suspend fun subscribeToBook(@Body request: BookSubscriptionRequest)

    @POST("bookSub/book/unsubscribe")
    suspend fun unsubscribeToBook(@Body request: BookSubscriptionRequest)

    @GET("bookSub/user/{userId}/book/{bookId}/isSubscribed")
    suspend fun isSubscribedToBook(@Path("userId") userId: Int, @Path("bookId") bookId: Int): Response<SubscriptionResponse>
}