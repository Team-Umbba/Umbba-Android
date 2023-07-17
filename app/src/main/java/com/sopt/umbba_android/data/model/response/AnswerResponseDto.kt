package com.sopt.umbba_android.data.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AnswerResponseDto(
    @SerialName("status")
    val status: Int,
    @SerialName("message")
    val message: String
)