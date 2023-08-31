package com.ubcompany.umbba_android.data.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class QuestionAnswerResponseDto(
    @SerialName("status")
    val status: Int,
    @SerialName("message")
    val message: String,
    @SerialName("data")
    val data: QnaData
) {
    @Serializable
    data class QnaData(
        @SerialName("qna_id")
        val qnaId: Long?,
        @SerialName("index")
        val index: Int?,
        @SerialName("section")
        val section: String?,
        @SerialName("topic")
        val topic: String?,
        @SerialName("opponent_question")
        val opponentQuestion: String?,
        @SerialName("my_question")
        val myQuestion: String?,
        @SerialName("opponent_answer")
        val opponentAnswer: String?,
        @SerialName("my_answer")
        val myAnswer: String?,
        @SerialName("is_opponent_answer")
        val isOpponentAnswer: Boolean?,
        @SerialName("is_my_answer")
        val isMyAnswer: Boolean?,
        @SerialName("opponent_username")
        val opponentUsername: String?,
        @SerialName("my_username")
        val myUsername: String?
    )
}