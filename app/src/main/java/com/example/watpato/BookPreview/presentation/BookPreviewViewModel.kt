package com.example.watpato.BookPreview.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.watpato.BookPreview.data.model.entities.Book
import com.example.watpato.BookPreview.domain.BookPreviewUseCase
import kotlinx.coroutines.launch

class BookPreviewViewModel : ViewModel() {
    private val bookPreviewUseCase = BookPreviewUseCase()

    private val _book = MutableLiveData<Book?>()
    val book: LiveData<Book?> = _book

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getBook(bookId: Int) {
        _isLoading.value = true
        viewModelScope.launch {
            val result = bookPreviewUseCase(bookId)
            result.onSuccess { fetchedBook ->
                _book.value = fetchedBook
                _errorMessage.value = null
            }.onFailure { exception ->
                _errorMessage.value = exception.message ?: "Error :O"
            }
            _isLoading.value = false
        }
    }
}