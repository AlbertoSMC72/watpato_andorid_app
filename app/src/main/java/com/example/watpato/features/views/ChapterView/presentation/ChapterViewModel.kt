package com.example.watpato.features.views.ChapterView.presentation

import android.app.Application
import android.content.Intent
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.watpato.features.views.ChapterView.data.model.Chapter
import com.example.watpato.features.views.ChapterView.data.repository.local.LocalChapterRepository
import com.example.watpato.features.views.ChapterView.domain.ChapterViewUseCase
import com.example.watpato.features.views.ChapterView.domain.DownloadService
import kotlinx.coroutines.launch

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

    // --- NUEVA IMPLEMENTACIÓN ---
    fun saveChapter(chapter: Chapter) {
        Log.d("ChapterViewModel", "Iniciando ForegroundService con: $chapter")

        // 1) Obtenemos el contexto de la aplicación
        val context = getApplication<Application>().applicationContext

        // 2) Creamos el Intent para arrancar nuestro DownloadService
        val serviceIntent = Intent(context, DownloadService::class.java).apply {
            putExtra("CHAPTER_TITLE", chapter.title)
            putExtra("CHAPTER_CONTENT", chapter.content)
            putExtra("CHAPTER_DATE", chapter.created_at)
        }


        ContextCompat.startForegroundService(context, serviceIntent)
        _isSaved.value = false
    }
}
