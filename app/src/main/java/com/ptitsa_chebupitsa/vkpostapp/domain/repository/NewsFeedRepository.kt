package com.ptitsa_chebupitsa.vkpostapp.domain.repository

import com.ptitsa_chebupitsa.vkpostapp.domain.entity.AuthState
import com.ptitsa_chebupitsa.vkpostapp.domain.entity.FeedPost
import com.ptitsa_chebupitsa.vkpostapp.domain.entity.PostComment
import kotlinx.coroutines.flow.StateFlow

interface NewsFeedRepository {

    fun getAuthStateFlow(): StateFlow<AuthState>

    fun getRecommendations(): StateFlow<List<FeedPost>>

    fun getComments(feedPost: FeedPost): StateFlow<List<PostComment>>

    suspend fun checkAuthState()

    suspend fun loadNextData()

    suspend fun deletePost(feedPost: FeedPost)

    suspend fun changeLikeStatus(feedPost: FeedPost)


}