package com.example.watpato.core.network

import com.example.watpato.register.data.datasource.RegisterService
import com.example.watpato.addBook.data.datasource.CreateBookService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {

    private const val BASE_URL = "https://zkz7p678-3000.usw3.devtunnels.ms/"

    // Instancia de Retrofit para toda la app
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

    // Servicio de registro que ya tenías
    val registerService: RegisterService by lazy {
        retrofit.create(RegisterService::class.java)
    }

    // NUEVO: servicio para crear libros y géneros
    val createBookService: CreateBookService by lazy {
        retrofit.create(CreateBookService::class.java)
    }
}
