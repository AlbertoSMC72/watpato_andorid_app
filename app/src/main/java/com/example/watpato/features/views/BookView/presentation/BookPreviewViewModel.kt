package com.example.watpato.features.views.BookView.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.watpato.features.views.BookView.data.model.entities.Book
import com.example.watpato.features.views.BookView.domain.BookPreviewUseCase
import kotlinx.coroutines.launch

class BookPreviewViewModel : ViewModel() {
    private val bookPreviewUseCase = BookPreviewUseCase()

    private val _book = MutableLiveData<Book?>()
    val book: LiveData<Book?> = _book

    private val _isSubscribed = MutableLiveData<Boolean>()
    val isSubscribed: LiveData<Boolean> = _isSubscribed

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

    fun checkSubscription(userId: Int, bookId: Int) {
        viewModelScope.launch {
            bookPreviewUseCase.isSubscribed(userId, bookId).onSuccess {
                _isSubscribed.value = it
            }
        }
    }

    fun toggleSubscription(userId: Int, bookId: Int) {
        viewModelScope.launch {
            if (_isSubscribed.value == true) {
                bookPreviewUseCase.unsubscribe(userId, bookId).onSuccess {
                    _isSubscribed.value = false
                }
            } else {
                bookPreviewUseCase.subscribe(userId, bookId).onSuccess {
                    _isSubscribed.value = true
                }
            }
        }
    }
}