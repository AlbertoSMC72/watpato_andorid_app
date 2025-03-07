package com.example.watpato.profile.data.datasource

import com.example.watpato.profile.data.model.requests.UserSubscriptionRequest
import com.example.watpato.profile.data.model.entities.User
import com.example.watpato.profile.data.model.entities.UserProfile
import com.example.watpato.profile.data.model.subscriptions.BookSubscription
import com.example.watpato.profile.data.model.subscriptions.UserSubscription
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

import retrofit2.http.Path

interface Profile {
    @GET("writerSub/{id}")
    suspend fun getUserSubscriptions(@Path("id") userId: Int): Response<UserSubscription>

    @GET("bookSub/{id}")
    suspend fun getBookSubscriptions(@Path("id") userId: Int): Response<BookSubscription>

    @POST("user/subscribe")
    suspend fun subscribeToUser(@Body request: UserSubscriptionRequest)

    @POST("user/unsubscribe")
    suspend fun unsubscribeToUser(@Body request: UserSubscriptionRequest)

    @GET("users/{id}")
    suspend fun getUser(@Path("id") userId: Int): Response<User>

    @GET("books/author/{id}")
    suspend fun getBooksByAuthor(@Path("id") authorId: Int): Response<UserProfile>
}