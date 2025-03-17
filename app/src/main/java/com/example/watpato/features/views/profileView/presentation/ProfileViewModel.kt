package com.example.watpato.features.views.profileView.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.watpato.core.local.chapters.dao.ChapterDAO
import com.example.watpato.core.local.chapters.entities.ChapterEntity
import com.example.watpato.features.views.profileView.data.model.entities.UserProfile
import com.example.watpato.features.views.profileView.data.model.subscriptions.BookSubscription
import com.example.watpato.features.views.profileView.data.model.subscriptions.UserSubscription
import com.example.watpato.features.views.profileView.domain.BookSubscriptionUseCase
import com.example.watpato.features.views.profileView.domain.ProfileUseCase
import com.example.watpato.features.views.profileView.domain.UserSubscriptionUseCase
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val bookSubscriptionUseCase: BookSubscriptionUseCase,
    private val userSubscriptionUseCase: UserSubscriptionUseCase,
    private val profileUseCase: ProfileUseCase,
    private val chapterDAO: ChapterDAO
) : ViewModel() {

    private val _bookSubscriptions = MutableLiveData<Result<BookSubscription>>()
    val bookSubscriptions: LiveData<Result<BookSubscription>> = _bookSubscriptions

    private val _userSubscriptions = MutableLiveData<Result<UserSubscription>>()
    val userSubscriptions: LiveData<Result<UserSubscription>> = _userSubscriptions

    private val _booksByUser = MutableLiveData<Result<UserProfile>>()
    val booksByUser: LiveData<Result<UserProfile>> = _booksByUser

    private val _isSubscribed = MutableLiveData<Boolean>()
    val isSubscribed: LiveData<Boolean> = _isSubscribed

    private val _downloadedChapters = MutableLiveData<Result<List<ChapterEntity>>>()
    val downloadedChapters: LiveData<Result<List<ChapterEntity>>> = _downloadedChapters

    fun loadUserProfile(userId: Int) {
        viewModelScope.launch {
            _booksByUser.postValue(profileUseCase.getBooksByAuthor(userId))
        }
    }

    fun loadSubscriptions(userId: Int) {
        viewModelScope.launch {
            _bookSubscriptions.postValue(bookSubscriptionUseCase.getBookSubscriptions(userId))
            _userSubscriptions.postValue(userSubscriptionUseCase.getUserSubscriptions(userId))
        }
    }

    fun loadDownloadedChapters() {
        viewModelScope.launch {
            try {
                val chapters = chapterDAO.getAllChapters()
                _downloadedChapters.postValue(Result.success(chapters))
            } catch (e: Exception) {
                _downloadedChapters.postValue(Result.failure(e))
            }
        }
    }
}