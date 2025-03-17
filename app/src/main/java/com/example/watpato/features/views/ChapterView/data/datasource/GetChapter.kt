package com.example.watpato.features.views.ChapterView.data.datasource

import com.example.watpato.features.views.ChapterView.data.model.Chapter
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface GetChapter {
    @GET("chapters/{id}")
    suspend fun getChapterById(@Path("id") chapterId: Int): Response<Chapter>
}