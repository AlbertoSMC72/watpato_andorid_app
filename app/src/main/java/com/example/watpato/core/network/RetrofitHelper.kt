package com.example.watpato.core.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {

    private const val BASE_URL = "https://zkz7p678-3000.usw3.devtunnels.ms/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // Ejemplo de método genérico si quisieras usarlo en otros lados
    fun <T> createService(service: Class<T>): T {
        return retrofit.create(service)
    }
}