package com.ptitsa_chebupitsa.vkpostapp.data.model

import com.google.gson.annotations.SerializedName

data class CommentsResponseDto(
    @SerializedName("response") val comments: CommentsContentDto

)
