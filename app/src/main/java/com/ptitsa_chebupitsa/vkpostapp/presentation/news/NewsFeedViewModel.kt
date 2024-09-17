package com.ptitsa_chebupitsa.vkpostapp.presentation.news

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ptitsa_chebupitsa.vkpostapp.data.repository.NewsFeedRepository
import com.ptitsa_chebupitsa.vkpostapp.domain.FeedPost
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

class NewsFeedViewModel(application: Application) : AndroidViewModel(application) {

    private val initialState = NewsFeedScreenState.Initial

    private val _screenState = MutableLiveData<NewsFeedScreenState>(initialState)
    val screenState: LiveData<NewsFeedScreenState> = _screenState

    private val repository = NewsFeedRepository(application)

    init {
        _screenState.value = NewsFeedScreenState.Loading
        loadRecommendations()
    }

    private fun loadRecommendations() {
        viewModelScope.launch {
            repository.recommendations
                .filter { it.isNotEmpty() }
                .collect { feedPosts ->
                _screenState.value = NewsFeedScreenState.Posts(posts = feedPosts)
            }

        }
    }

    fun loadNextRecommendations() {
        viewModelScope.launch {
            _screenState.value = NewsFeedScreenState.Posts(
                posts = repository.feedPosts,
                nextDataIsLoading = true
            )
            viewModelScope.launch {
                repository.loadNextData()
            }
        }
    }


    fun changeLikeStatus(feedPost: FeedPost) {
        viewModelScope.launch {
            try {
                repository.changeLikeStatus(feedPost)
                _screenState.value = NewsFeedScreenState.Posts(posts = repository.feedPosts)
            } catch (e: Exception) {
                Log.e("NewsFeedViewModel", "Ошибка при изменении статуса лайка", e)
                // Обработайте ошибку, возможно, обновите UI, чтобы показать сообщение об ошибке
            }
        }
    }


    fun remove(feedPost: FeedPost) {
        viewModelScope.launch {
            repository.deletePost(feedPost)
            _screenState.value = NewsFeedScreenState.Posts(posts = repository.feedPosts)
        }
    }

}

