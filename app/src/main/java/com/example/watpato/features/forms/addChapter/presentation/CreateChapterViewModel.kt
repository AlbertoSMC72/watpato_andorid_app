package com.example.watpato.features.forms.addChapter.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.watpato.features.forms.addChapter.data.model.ChapterRequest
import com.example.watpato.features.forms.addChapter.domain.CreateChapterUseCase
import kotlinx.coroutines.launch

class CreateChapterViewModel(bookId: Int) : ViewModel() {

    private val createChapterUseCase = CreateChapterUseCase()

    private val _title = MutableLiveData<String>("")
    val title: LiveData<String> = _title

    private val _content = MutableLiveData<String>("")
    val content: LiveData<String> = _content

    private val _bookId = MutableLiveData<Int>(bookId)
    val bookId: LiveData<Int> = _bookId

    private val _error = MutableLiveData<String>("")
    val error: LiveData<String> = _error

    private val _success = MutableLiveData<Boolean>(false)
    val success: LiveData<Boolean> = _success

    fun onTitleChange(newTitle: String) {
        _title.value = newTitle
    }

    fun onContentChange(newContent: String) {
        _content.value = newContent
    }

    fun createChapter() {
        val currentTitle = _title.value?.trim().orEmpty()
        val currentContent = _content.value?.trim().orEmpty()
        val currentBookId = _bookId.value ?: -1

        if (currentTitle.isBlank() || currentContent.isBlank()) {
            _error.value = "Por favor, completa todos los campos"
            return
        }
        if (currentBookId <= 0) {
            _error.value = "Error con el ID del libro"
            return
        }

        viewModelScope.launch {
            val request = ChapterRequest(
                title = currentTitle,
                content = currentContent,
                bookId = currentBookId
            )

            val result = createChapterUseCase(request)
            result.onSuccess {
                _success.value = true
                _error.value = ""
            }.onFailure { exception ->
                _success.value = false
                _error.value = "Error creando capítulo: ${exception.message}"
            }
        }
    }
}
