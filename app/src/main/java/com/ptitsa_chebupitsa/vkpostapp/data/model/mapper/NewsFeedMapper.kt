package com.ptitsa_chebupitsa.vkpostapp.data.model.mapper

import com.ptitsa_chebupitsa.vkpostapp.data.model.CommentsResponseDto
import com.ptitsa_chebupitsa.vkpostapp.data.model.NewsFeedResponseDto
import com.ptitsa_chebupitsa.vkpostapp.domain.FeedPost
import com.ptitsa_chebupitsa.vkpostapp.domain.PostComment
import com.ptitsa_chebupitsa.vkpostapp.domain.StatisticItem
import com.ptitsa_chebupitsa.vkpostapp.domain.StatisticType
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.absoluteValue


class NewsFeedMapper {

    fun mapResponseToPosts(responseDto: NewsFeedResponseDto): List<FeedPost> {
        val result = mutableListOf<FeedPost>()

        val posts = responseDto.newsFeedContent.posts
        val groups = responseDto.newsFeedContent.groups

        for (post in posts) {
            val group = groups.find { it.id == post.communityId.absoluteValue } ?: break
            val feedPost = FeedPost(
                id = post.id,
                communityId = post.communityId,
                communityName = group.name,
                publicationDate = mapTimestampToDate(post.date * 1000),
                communityImageUrl = group.imageUrl,
                contentText = post.text,
                contentImageUrl = post.attachments?.firstOrNull()?.photo?.photoUrls?.lastOrNull()?.url,
                statistics = listOf(
                    StatisticItem(type = StatisticType.LIKES, post.likes.count),
                    StatisticItem(type = StatisticType.VIEWS, post.views.count),
                    StatisticItem(type = StatisticType.SHARES, post.reposts.count),
                    StatisticItem(type = StatisticType.COMMENTS, post.comments.count)
                ),
                isLiked = post.likes.userLikes > 0
            )
            result.add(feedPost)
        }
        return result
    }

    fun mapResponseToComments(response: CommentsResponseDto): List<PostComment> {
        val result = mutableListOf<PostComment>()
        val comments = response.comments.comments
        val profiles = response.comments.profiles
        for (comment in comments) {
            if (comment.text.isBlank()) continue
            val author = profiles.firstOrNull() { it.id == comment.authorId } ?: continue
            val postComment = PostComment(
                id = comment.id,
                authorName = "${author.firstName} ${author.lastName}",
                authorAvatarUrl = author.avatarUrl,
                commentText = comment.text,
                publicationTime = mapTimestampToDate(comment.date)
            )
            result.add(postComment)
        }
        return result
    }

    private fun mapTimestampToDate(timestamp: Long): String {
        val date = Date(timestamp * 1000)
        return SimpleDateFormat("d MMMM yyyy, hh:mm", Locale.getDefault()).format(date)
    }
}
