package com.ptitsa_chebupitsa.vkpostapp.domain.usecases

import com.ptitsa_chebupitsa.vkpostapp.domain.entity.FeedPost
import com.ptitsa_chebupitsa.vkpostapp.domain.repository.NewsFeedRepository
import kotlinx.coroutines.flow.StateFlow

class GetRecommendationsUseCase (
    private val repository: NewsFeedRepository
){
    operator fun invoke(): StateFlow<List<FeedPost>> {
       return repository.getRecommendations()
    }
}