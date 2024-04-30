package com.ubcompany.umbba_android.data.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RecordImageResponseDto(
    @SerialName("status")
    val status: Int,
    @SerialName("message")
    val message: String,
    @SerialName("data")
    val data: RecordImageData
) {
    @Serializable
    data class RecordImageData(
        @SerialName("file_name")
        val fileName: String,
        @SerialName("url")
        val url: String
    )
}
