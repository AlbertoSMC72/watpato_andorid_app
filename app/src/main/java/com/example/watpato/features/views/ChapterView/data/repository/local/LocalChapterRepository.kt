package com.example.watpato.features.views.ChapterView.data.repository.local

import com.example.watpato.core.local.appDatabase.DatabaseProvider
import android.content.Context
import com.example.watpato.core.local.chapters.entities.ChapterEntity

class LocalChapterRepository(context: Context) {
    private val chapterDAO = DatabaseProvider.getAppDataBase(context).chapterDAO()

    suspend fun saveChapter(chapter: ChapterEntity){
        chapterDAO.saveChapter(chapter)
    }

}