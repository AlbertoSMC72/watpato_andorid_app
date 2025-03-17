package com.example.watpato.features.views.ChapterView.data.repository

import com.example.watpato.features.views.ChapterView.data.datasource.GetChapter
import com.example.watpato.features.views.ChapterView.data.model.Chapter
import com.example.watpato.core.network.RetrofitHelper

class ChapterRepository {
    private val chapterService = RetrofitHelper.createService(GetChapter::class.java)

    suspend fun getChapterById(id: Int): Result<Chapter> {
        return try {
            val response = chapterService.getChapterById(id)
            if (response.isSuccessful) {
                response.body()?.let { Result.success(it) } ?: Result.failure(Exception("Capitulo no encontrado"))
            } else {
                Result.failure(Exception("Error al obtener el capitulo"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}