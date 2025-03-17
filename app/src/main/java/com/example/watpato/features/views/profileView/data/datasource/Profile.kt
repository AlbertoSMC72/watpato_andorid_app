package com.example.watpato.features.views.profileView.data.datasource

import com.example.watpato.features.views.profileView.data.model.requests.UserSubscriptionRequest
import com.example.watpato.features.views.profileView.data.model.entities.User
import com.example.watpato.features.views.profileView.data.model.entities.UserProfile
import com.example.watpato.features.views.profileView.data.model.response.SubscriptionResponse
import com.example.watpato.features.views.profileView.data.model.subscriptions.BookSubscription
import com.example.watpato.features.views.profileView.data.model.subscriptions.UserSubscription
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

    @POST("writerSub/user/subscribe")
    suspend fun subscribeToUser(@Body request: UserSubscriptionRequest)

    @POST("writerSub/user/unsubscribe")
    suspend fun unsubscribeToUser(@Body request: UserSubscriptionRequest)

    @GET("users/{id}")
    suspend fun getUser(@Path("id") userId: Int): Response<User>

    @GET("books/author/{id}")
    suspend fun getBooksByAuthor(@Path("id") authorId: Int): Response<UserProfile>

    @GET("writerSub/user/{userId}/writer/{writerId}/isSubscribed")
    suspend fun isSubscribedToUser(@Path("userId") userId: Int, @Path("writerId") writerId: Int): Response<SubscriptionResponse>
}