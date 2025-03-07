package com.example.watpato.profile.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.watpato.profile.data.model.entities.UserProfile
import com.example.watpato.profile.data.model.subscriptions.BookSubscription
import com.example.watpato.profile.data.model.subscriptions.UserSubscription
import com.example.watpato.profile.domain.BookSubscriptionUseCase
import com.example.watpato.profile.domain.ProfileUseCase
import com.example.watpato.profile.domain.UserSubscriptionUseCase
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val bookSubscriptionUseCase: BookSubscriptionUseCase,
    private val userSubscriptionUseCase: UserSubscriptionUseCase,
    private val profileUseCase: ProfileUseCase
) : ViewModel() {

    private val _bookSubscriptions = MutableLiveData<Result<BookSubscription>>()
    val bookSubscriptions: LiveData<Result<BookSubscription>> = _bookSubscriptions

    private val _userSubscriptions = MutableLiveData<Result<UserSubscription>>()
    val userSubscriptions: LiveData<Result<UserSubscription>> = _userSubscriptions

    private val _booksByUser = MutableLiveData<Result<UserProfile>>()
    val booksByUser: LiveData<Result<UserProfile>> = _booksByUser

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
}