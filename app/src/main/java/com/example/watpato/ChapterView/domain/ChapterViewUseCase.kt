package com.example.watpato.ChapterView.domain

import com.example.watpato.ChapterView.data.model.Chapter
import com.example.watpato.ChapterView.data.repository.ChapterRepository

class ChapterViewUseCase {
    private val chapterRepository = ChapterRepository()

    suspend operator fun invoke(chapterId: Int): Result<Chapter> {
        return chapterRepository.getChapterById(chapterId)
    }
}