package com.example.watpato.features.home.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.watpato.features.home.data.model.Book
import com.example.watpato.features.home.domain.GetAllBooksUseCase
import kotlinx.coroutines.launch

class HomeViewModel: ViewModel() {
    private val getAllBooksUseCase = GetAllBooksUseCase()

    private val _books = MutableLiveData<List<Book>?>()
    val books: LiveData<List<Book>?> = _books

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getAllBooks() {
        _isLoading.value = true
        viewModelScope.launch {
            val result = getAllBooksUseCase()
            result.onSuccess { fetchedBooks ->
                _books.value = fetchedBooks
                _errorMessage.value = null
            }.onFailure { exception ->
                _errorMessage.value = exception.message ?: "Error :O"
            }
            _isLoading.value = false
        }
    }
}