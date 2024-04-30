package com.ubcompany.umbba_android.data.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RecordListResponseDto(
    @SerialName("status")
    val status: Int,
    @SerialName("message")
    val message: String,
    @SerialName("data")
    val data: List<RecordListData>
) {
    @Serializable
    data class RecordListData(
        @SerialName("album_id")
        val id: Int,
        @SerialName("title")
        val title: String,
        @SerialName("content")
        val content: String,
        @SerialName("writer")
        val writer: String,
        @SerialName("img_url")
        val imgUrl: String
    )
}

