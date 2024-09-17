package com.ptitsa_chebupitsa.vkpostapp.presentation.comments

import android.app.Application
import androidx.lifecycle.ViewModel
import com.ptitsa_chebupitsa.vkpostapp.data.repository.NewsFeedRepository
import com.ptitsa_chebupitsa.vkpostapp.domain.FeedPost
import kotlinx.coroutines.flow.map

class CommentsViewModel(feedPost: FeedPost, application: Application) : ViewModel() {
    private val repository = NewsFeedRepository(application)

    val screenState = repository.getComments(feedPost)
        .map {
            CommentsScreenState.Comments(
                feedPost = feedPost,
                comments = it
            )
        }
}