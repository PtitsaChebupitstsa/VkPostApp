package com.ptitsa_chebupitsa.vkpostapp.data.model

import com.google.gson.annotations.SerializedName

data class LikesCountDto (
    @SerializedName("likes") val count : Int
)
