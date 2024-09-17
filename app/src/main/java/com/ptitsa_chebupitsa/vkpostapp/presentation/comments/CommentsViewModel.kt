package com.ptitsa_chebupitsa.vkpostapp.presentation.comments

import android.app.Application
import androidx.lifecycle.ViewModel
import com.ptitsa_chebupitsa.vkpostapp.data.repository.NewsFeedRepositoryImpl
import com.ptitsa_chebupitsa.vkpostapp.domain.entity.FeedPost
import com.ptitsa_chebupitsa.vkpostapp.domain.usecases.GetCommentsUseCase
import kotlinx.coroutines.flow.map

class CommentsViewModel(feedPost: FeedPost, application: Application) : ViewModel() {
    private val repository = NewsFeedRepositoryImpl(application)

    private val getCommentsUseCase = GetCommentsUseCase(repository)

    val screenState = getCommentsUseCase(feedPost)
        .map {
            CommentsScreenState.Comments(
                feedPost = feedPost,
                comments = it
            )
        }
}