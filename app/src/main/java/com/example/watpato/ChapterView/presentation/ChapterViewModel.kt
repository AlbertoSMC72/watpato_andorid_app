package com.example.watpato.ChapterView.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.example.watpato.ChapterView.data.model.Chapter
import com.example.watpato.ChapterView.domain.ChapterViewUseCase

class ChapterViewModel : ViewModel() {
    private val chapterViewUseCase = ChapterViewUseCase()

    private val _chapter = MutableLiveData<Chapter?>()
    val chapter: LiveData<Chapter?> = _chapter

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

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
}