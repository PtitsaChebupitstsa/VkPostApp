package com.ptitsa_chebupitsa.vkpostapp.data.model

import com.google.gson.annotations.SerializedName

data class PhotoDto(
    @SerializedName("sizes") val photoUrls: List<PhotoUrlDto>
)
