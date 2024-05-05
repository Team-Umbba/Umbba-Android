package com.ubcompany.umbba_android.data.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RefreshQuestionRequestDto(
    @SerialName("question_id")
    val questionId: Long
)
