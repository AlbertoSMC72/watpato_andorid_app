package com.example.watpato.addChapter.data.datasource

import com.example.watpato.addChapter.data.model.ChapterRequest

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ChapterService {

    @POST("chapters/")
    suspend fun createChapter(@Body chapter: ChapterRequest): Response<Unit>
}
