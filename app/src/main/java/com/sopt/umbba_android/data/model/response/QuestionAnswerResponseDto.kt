package com.sopt.umbba_android.data.model.response

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
){
    @Serializable
    data class QnaData(
        @SerialName("is_my_answer")
        val isMyAnswer: Boolean,
        @SerialName("is_opponent_answer")
        val isOpponentAnswer: Boolean,
        @SerialName("my_answer")
        val myAnswer: String,
        @SerialName("my_question")
        val myQuestion: String,
        @SerialName("my_username")
        val myUsername: String,
        @SerialName("opponent_answer")
        val opponentAnswer: String,
        @SerialName("opponent_question")
        val opponentQuestion: String,
        @SerialName("opponent_username")
        val opponentUsername: String,
        @SerialName("qna_id")
        val qnaId: Long,
        @SerialName("section")
        val section: String,
        @SerialName("topic")
        val topic: String
    )
}