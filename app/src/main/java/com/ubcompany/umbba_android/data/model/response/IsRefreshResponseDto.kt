package com.ubcompany.umbba_android.data.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class IsRefreshResponseDto(
    @SerialName("status")
    val status: Int,
    @SerialName("message")
    val message: String,
    @SerialName("data")
    val data: RefreshData
) {
    @Serializable
    data class RefreshData(
        @SerialName("question_id")
        val questionId: Long,
        @SerialName("new_question")
        val newQuestion: String
    )
}