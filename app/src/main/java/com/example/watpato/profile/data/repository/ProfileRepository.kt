package com.example.watpato.profile.data.repository

import com.example.watpato.profile.data.datasource.Profile
import com.example.watpato.profile.data.model.entities.User
import com.example.watpato.profile.data.model.entities.UserProfile
import com.example.watpato.profile.data.model.requests.UserSubscriptionRequest
import com.example.watpato.profile.data.model.subscriptions.BookSubscription
import com.example.watpato.profile.data.model.subscriptions.UserSubscription
import com.example.watpato.core.network.RetrofitHelper

class ProfileRepository {
    private val profileService = RetrofitHelper.createService(Profile::class.java)

    suspend fun getUserSubscriptions(id: Int): Result<UserSubscription> {
        return try {
            val response = profileService.getUserSubscriptions(id)
            if (response.isSuccessful) {
                response.body()?.let { Result.success(it) }
                    ?: Result.failure(Exception("Seguimiento de usuarios no encontrado"))
            } else {
                Result.failure(Exception("Error al obtener las subscripciones"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getBookSubscriptions(id: Int): Result<BookSubscription> {
        return try {
            val response = profileService.getBookSubscriptions(id)
            if (response.isSuccessful) {
                response.body()?.let { Result.success(it) } ?: Result.failure(Exception("Seguimiento de libros no encontrado"))
            } else {
                Result.failure(Exception("Error al obtener las subscripciones"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun subscribeToUser(userId: Int, writerId: Int): Result<Unit> {
        return try {
            val request = UserSubscriptionRequest(userId, writerId)
            profileService.subscribeToUser(request)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun unsubscribeToUser(userId: Int, writerId: Int): Result<Unit> {
        return try {
            val request = UserSubscriptionRequest(userId, writerId)
            profileService.unsubscribeToUser(request)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getUser(userId: Int): Result<User> {
        return try {
            val response = profileService.getUser(userId)
            if (response.isSuccessful) {
                response.body()?.let { Result.success(it) } ?: Result.failure(Exception("Usuario no encontrado"))
            } else {
                Result.failure(Exception("Error al obtener el usuario"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getBooksByAuthor(authorId: Int): Result<UserProfile> {
        return try {
            val response = profileService.getBooksByAuthor(authorId)
            if (response.isSuccessful) {
                response.body()?.let { Result.success(it) } ?: Result.failure(Exception("Libros no encontrados"))
            } else {
                Result.failure(Exception("Error al obtener los libros del autor"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun isSubscribedToUser(userId: Int, writerId: Int): Result<Boolean> {
        return try {
            val response = profileService.isSubscribedToUser(userId, writerId)
            if (response.isSuccessful) {
                val subscriptionResponse = response.body()
                Result.success(subscriptionResponse?.isSubscribed ?: false)
            } else {
                Result.failure(Exception("Error al verificar suscripción"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}