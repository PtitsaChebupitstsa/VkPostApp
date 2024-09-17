package com.ptitsa_chebupitsa.vkpostapp.presentation.comments

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ptitsa_chebupitsa.vkpostapp.data.repository.NewsFeedRepository
import com.ptitsa_chebupitsa.vkpostapp.domain.FeedPost
import kotlinx.coroutines.launch

class CommentsViewModel(feedPost: FeedPost, application: Application) : ViewModel() {
    private val _screenState = MutableLiveData<CommentsScreenState>(CommentsScreenState.Initial)
    val screenState: LiveData<CommentsScreenState> = _screenState

    private val repository = NewsFeedRepository(application)


    init {
        loadComments(feedPost)
    }

    fun loadComments(feedPost: FeedPost) {
        viewModelScope.launch {
            val comments = repository.getComments(feedPost)
            _screenState.value =
                CommentsScreenState.Comments(feedPost = feedPost, comments = comments)
        }
    }

}