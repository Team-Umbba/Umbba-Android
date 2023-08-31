package com.sopt.umbba_android.data.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ListResponseDto(
    @SerialName("status")
    val status: Int,
    @SerialName("message")
    val message: String,
    @SerialName("data")
    val data: List<ListData>
) {
    @Serializable
    data class ListData(
        @SerialName("index")
        val index: Int,
        @SerialName("qna_id")
        val qnaId: Long,
        @SerialName("topic")
        val topic: String
    )
}