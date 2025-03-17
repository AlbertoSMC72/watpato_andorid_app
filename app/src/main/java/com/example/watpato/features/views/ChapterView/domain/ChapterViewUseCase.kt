package com.example.watpato.features.views.ChapterView.domain

import com.example.watpato.features.views.ChapterView.data.model.Chapter
import com.example.watpato.features.views.ChapterView.data.repository.ChapterRepository

class ChapterViewUseCase {
    private val chapterRepository = ChapterRepository()

    suspend operator fun invoke(chapterId: Int): Result<Chapter> {
        return chapterRepository.getChapterById(chapterId)
    }
}