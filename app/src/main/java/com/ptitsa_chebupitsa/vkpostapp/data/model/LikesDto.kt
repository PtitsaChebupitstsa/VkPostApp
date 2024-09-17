package com.ptitsa_chebupitsa.vkpostapp.data.model

import com.google.gson.annotations.SerializedName

data class LikesDto(
    @SerializedName("count") val count: Int,
    @SerializedName("user_likes") val userLikes: Int
)
