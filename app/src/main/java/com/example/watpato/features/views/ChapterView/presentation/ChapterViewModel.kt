package com.example.watpato.features.views.ChapterView.presentation

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.watpato.core.local.chapters.entities.ChapterEntity
import kotlinx.coroutines.launch
import com.example.watpato.features.views.ChapterView.data.model.Chapter
import com.example.watpato.features.views.ChapterView.domain.ChapterViewUseCase
import com.example.watpato.features.views.ChapterView.data.repository.local.LocalChapterRepository

class ChapterViewModel(application: Application) : AndroidViewModel(application) {
    private val chapterViewUseCase = ChapterViewUseCase()
    private val localChapterRepository = LocalChapterRepository(application)

    private val _chapter = MutableLiveData<Chapter?>()
    val chapter: LiveData<Chapter?> = _chapter

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isSaved = MutableLiveData<Boolean>()
    val isSaved: LiveData<Boolean> = _isSaved

    fun getChapter(chapterId: Int) {
        _isLoading.value = true
        viewModelScope.launch {
            val result = chapterViewUseCase(chapterId)
            result.onSuccess { fetchedChapter ->
                _chapter.value = fetchedChapter
                _errorMessage.value = null
            }.onFailure { exception ->
                _errorMessage.value = exception.message ?: "Error D:"
            }
            _isLoading.value = false
        }
    }

    fun saveChapter(chapter: Chapter) {
        Log.d("ChapterViewModel", "Chapter value: $chapter")
        val entity = ChapterEntity(
            title = chapter.title,
            content = chapter.content,
            created_at = chapter.created_at
        )
        viewModelScope.launch {
            Log.d("ChapterViewModel", "Guardando capítulo: $chapter")
            try {
                localChapterRepository.saveChapter(entity)
                _isSaved.value = true
                Log.d("ChapterViewModel", "Capítulo guardado correctamente.")
            } catch (e: Exception) {
                Log.e("ChapterViewModel", "Error al guardar capítulo: ${e.message}")
            }
        }
    }

}