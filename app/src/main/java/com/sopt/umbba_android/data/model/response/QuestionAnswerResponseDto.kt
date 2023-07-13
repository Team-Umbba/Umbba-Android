package com.sopt.umbba_android.data.model.response

import kotlinx.serialization.Serializable

@Serializable
data class QuestionAnswerResponseDto(
    val section: String,
    val topic: String,
    val opponentQuestion: String,
    val myQuestion: String,
    val opponentAnswer: String,
    val myAnswer: String,
    val isOpponentAnswer: Boolean,
    val isMyAnswer: Boolean,
    val opponentUserName: String,
    val myUserName: String
)
