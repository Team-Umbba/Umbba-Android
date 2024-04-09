package com.ubcompany.umbba_android.data.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RecordImageRequestDto(
    @SerialName("img_prefix")
    val imgPrefix: String = "album/"
)
