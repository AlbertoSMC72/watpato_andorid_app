package com.example.watpato.features.forms.addChapter.data.repository

import com.example.watpato.features.forms.addChapter.data.datasource.ChapterService
import com.example.watpato.features.forms.addChapter.data.model.ChapterRequest
import com.example.watpato.core.network.RetrofitHelper

class CreateChapterRepository {

    private val chapterService: ChapterService = RetrofitHelper
        .createService(ChapterService::class.java)

    suspend fun createChapter(chapterRequest: ChapterRequest): Result<Unit> {
        return try {
            val response = chapterService.createChapter(chapterRequest)
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception(response.errorBody()?.string()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}