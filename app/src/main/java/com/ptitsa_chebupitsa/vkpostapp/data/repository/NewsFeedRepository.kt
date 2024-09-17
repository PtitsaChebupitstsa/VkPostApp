package com.ptitsa_chebupitsa.vkpostapp.data.repository

import android.app.Application
import android.util.Log
import com.ptitsa_chebupitsa.vkpostapp.data.model.mapper.NewsFeedMapper
import com.ptitsa_chebupitsa.vkpostapp.data.network.ApiFactory
import com.ptitsa_chebupitsa.vkpostapp.domain.FeedPost
import com.ptitsa_chebupitsa.vkpostapp.domain.PostComment
import com.ptitsa_chebupitsa.vkpostapp.domain.StatisticItem
import com.ptitsa_chebupitsa.vkpostapp.domain.StatisticType
import com.vk.api.sdk.VKPreferencesKeyValueStorage
import com.vk.api.sdk.auth.VKAccessToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn

class NewsFeedRepository(application: Application) {

    private val storage = VKPreferencesKeyValueStorage(application)
    private val token = VKAccessToken.restore(storage)

    private val apiService = ApiFactory.apiService
    private val mapper = NewsFeedMapper()

    private val coroutineScope = CoroutineScope(Dispatchers.Default)
    private val nextDateNeededEvent = MutableSharedFlow<Unit>(replay = 1)

    private val _feedPosts = mutableListOf<FeedPost>()
    val feedPosts: List<FeedPost>
        get() = _feedPosts.toList()

    private var nextFrom: String? = null

    val recommendations: Flow<List<FeedPost>> = flow {
        nextDateNeededEvent.emit(Unit)
        nextDateNeededEvent.collect {
            val startFrom = nextFrom
            if (startFrom == null && feedPosts.isNotEmpty()) {
                emit(feedPosts)
                return@collect
            }
            val response = if (startFrom == null) {
                apiService.loadRecommendations(getAccessToken())
            } else {
                apiService.loadRecommendations(getAccessToken(), startFrom)
            }
            nextFrom = response.newsFeedContent.nextFrom
            val posts = mapper.mapResponseToPosts(response)
            _feedPosts.addAll(posts)
            emit(feedPosts)
        }
    }.stateIn(
        scope = coroutineScope,
        started = SharingStarted.Lazily,
        initialValue = feedPosts
    )

    suspend fun loadNextData(){
        nextDateNeededEvent.emit(Unit)
    }

    private fun getAccessToken(): String {
        return token?.accessToken ?: throw IllegalStateException("Token is null")
    }

    suspend fun deletePost(feedPost: FeedPost) {
        apiService.ignorePost(
            accessToken = getAccessToken(),
            ownerId = feedPost.communityId,
            postId = feedPost.id
        )
        _feedPosts.remove(feedPost)
    }

    suspend fun getComments(feedPost: FeedPost): List<PostComment> {
        val comments = apiService.getComments(
            access_token = getAccessToken(),
            ownerId = feedPost.communityId,
            postId = feedPost.id
        )
        return mapper.mapResponseToComments(comments)
    }

    suspend fun changeLikeStatus(feedPost: FeedPost) {
        val response = if (feedPost.isLiked) {
            apiService.deleteLike(
                token = getAccessToken(),
                ownerId = feedPost.communityId,
                postId = feedPost.id
            )
        } else {
            apiService.addLike(
                token = getAccessToken(),
                ownerId = feedPost.communityId,
                postId = feedPost.id
            )
        }
        val newLikesCount = response.likes.count
        val newStatistics = feedPost.statistics.toMutableList().apply {
            removeIf { it.type == StatisticType.LIKES }
            add(StatisticItem(type = StatisticType.LIKES, newLikesCount))
        }
        val newPost = feedPost.copy(statistics = newStatistics, isLiked = !feedPost.isLiked)
        val postIndex = _feedPosts.indexOf(feedPost)
        _feedPosts[postIndex] = newPost
        Log.d("Test", "changeLikeStatus: $newPost")
        Log.d("Test", "changeLikeStatus: $newLikesCount")
    }
}