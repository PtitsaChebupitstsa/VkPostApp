package com.ptitsa_chebupitsa.vkpostapp.presentation.news

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.ptitsa_chebupitsa.vkpostapp.data.repository.NewsFeedRepositoryImpl
import com.ptitsa_chebupitsa.vkpostapp.domain.entity.FeedPost
import com.ptitsa_chebupitsa.vkpostapp.domain.usecases.ChangeLikeStatusUseCase
import com.ptitsa_chebupitsa.vkpostapp.domain.usecases.DeletePostUseCase
import com.ptitsa_chebupitsa.vkpostapp.domain.usecases.GetRecommendationsUseCase
import com.ptitsa_chebupitsa.vkpostapp.domain.usecases.LoadNextDataUseCase
import com.ptitsa_chebupitsa.vkpostapp.extanions.mergeWith
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class NewsFeedViewModel(application: Application) : AndroidViewModel(application) {
    private val exceptionHandler = CoroutineExceptionHandler{_,_->
        Log.d("NewsFeedViewModel", "Exception handled caught  ")
    }
    private val repository = NewsFeedRepositoryImpl(application)
    private val getRecommendationsUseCase = GetRecommendationsUseCase(repository)
    private val loadNextDataUseCase = LoadNextDataUseCase(repository)
    private val changeLikeStatusUseCase = ChangeLikeStatusUseCase(repository)
    private val deletePostUseCase = DeletePostUseCase(repository)


    private val recommendationsFlow = getRecommendationsUseCase()

    private val loadNextDataEvents = MutableSharedFlow<Unit>()
    private val loadNextDataFlow = flow {
        loadNextDataEvents.collect {
            emit(
                NewsFeedScreenState.Posts(
                    posts = recommendationsFlow.value,
                    nextDataIsLoading = true
                )
            )
        }
    }

    val screenState = recommendationsFlow
        .filter { it.isNotEmpty() }
        .map { NewsFeedScreenState.Posts(posts = it) as NewsFeedScreenState }
        .onStart { emit(NewsFeedScreenState.Loading) }
        .mergeWith(loadNextDataFlow)

    fun loadNextRecommendations() {
        viewModelScope.launch {
            loadNextDataEvents.emit(Unit)
            viewModelScope.launch {
              loadNextDataUseCase()
            }
        }
    }


    fun changeLikeStatus(feedPost: FeedPost) {
        viewModelScope.launch(exceptionHandler) {
           changeLikeStatusUseCase(feedPost)
        }
    }

        fun remove(feedPost: FeedPost) {
            viewModelScope.launch(exceptionHandler) {
               deletePostUseCase(feedPost)
            }
        }

}

