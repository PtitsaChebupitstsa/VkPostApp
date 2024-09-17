package com.ptitsa_chebupitsa.vkpostapp.domain.usecases

import com.ptitsa_chebupitsa.vkpostapp.domain.entity.FeedPost
import com.ptitsa_chebupitsa.vkpostapp.domain.repository.NewsFeedRepository

class DeletePostUseCase (
    private val repository: NewsFeedRepository
){
    suspend operator fun invoke(feedPost: FeedPost) {
       return repository.deletePost(feedPost)
    }
}