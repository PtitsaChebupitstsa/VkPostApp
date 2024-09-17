package com.ptitsa_chebupitsa.vkpostapp.presentation.news

import com.ptitsa_chebupitsa.vkpostapp.domain.entity.FeedPost

sealed class NewsFeedScreenState {

    data object Initial : NewsFeedScreenState()
    data object Loading : NewsFeedScreenState()

    data class Posts(
        val posts: List<FeedPost>,
        val nextDataIsLoading: Boolean = false
    ) : NewsFeedScreenState()


}

