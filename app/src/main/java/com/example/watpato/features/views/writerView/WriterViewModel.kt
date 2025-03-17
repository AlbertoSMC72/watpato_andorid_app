package com.example.watpato.features.views.writerView

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.watpato.features.views.profileView.data.model.entities.UserProfile
import com.example.watpato.features.views.profileView.domain.ProfileUseCase
import kotlinx.coroutines.launch

class WriterViewModel(
    private val profileUseCase: ProfileUseCase
) : ViewModel() {

    private val _userProfile = MutableLiveData<Result<UserProfile>>()
    val userProfile: LiveData<Result<UserProfile>> = _userProfile

    private val _isSubscribed = MutableLiveData<Boolean>()
    val isSubscribed: LiveData<Boolean> = _isSubscribed

    fun loadAuthorBooks(authorId: Int) {
        viewModelScope.launch {
            _userProfile.postValue(profileUseCase.getBooksByAuthor(authorId))
        }
    }

    fun checkSubscription(userId: Int, writerId: Int) {
        viewModelScope.launch {
            profileUseCase.isSubscribed(userId, writerId).onSuccess {
                _isSubscribed.value = it
            }
        }
    }

    fun toggleSubscription(userId: Int, writerId: Int) {
        viewModelScope.launch {
            if (_isSubscribed.value == true) {
                profileUseCase.unsubscribe(userId, writerId).onSuccess {
                    _isSubscribed.value = false
                    loadAuthorBooks(writerId)
                }
            } else {
                profileUseCase.subscribe(userId, writerId).onSuccess {
                    _isSubscribed.value = true
                    loadAuthorBooks(writerId)
                }
            }
        }
    }
}