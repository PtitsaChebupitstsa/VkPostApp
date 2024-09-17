package com.ptitsa_chebupitsa.vkpostapp.domain.usecases

import com.ptitsa_chebupitsa.vkpostapp.domain.entity.FeedPost
import com.ptitsa_chebupitsa.vkpostapp.domain.entity.PostComment
import com.ptitsa_chebupitsa.vkpostapp.domain.repository.NewsFeedRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

class GetCommentsUseCase (
    private val repository: NewsFeedRepository
){
    operator fun invoke(feedPost: FeedPost): StateFlow<List<PostComment>> {
       return repository.getComments(feedPost)
    }
}