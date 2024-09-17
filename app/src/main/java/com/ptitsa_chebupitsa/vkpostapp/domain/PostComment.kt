package com.ptitsa_chebupitsa.vkpostapp.domain

data class PostComment(
    val id: Long,
    val authorName: String,
    val authorAvatarUrl: String,
    val commentText: String,
    val publicationTime: String
)