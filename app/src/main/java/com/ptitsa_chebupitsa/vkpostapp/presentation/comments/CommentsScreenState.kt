package com.ptitsa_chebupitsa.vkpostapp.presentation.comments

import com.ptitsa_chebupitsa.vkpostapp.domain.entity.FeedPost
import com.ptitsa_chebupitsa.vkpostapp.domain.entity.PostComment

sealed class CommentsScreenState {

    data object Initial : CommentsScreenState()

    data class Comments(val feedPost: FeedPost, val comments: List<PostComment>) : CommentsScreenState()

}

