package com.example.watpato.addChapter.domain

import com.example.watpato.addChapter.data.model.ChapterRequest
import com.example.watpato.addChapter.data.repository.CreateChapterRepository

class CreateChapterUseCase(
    private val repository: CreateChapterRepository = CreateChapterRepository()
) {
    suspend operator fun invoke(chapterRequest: ChapterRequest): Result<Unit> {
        return repository.createChapter(chapterRequest)
    }
}