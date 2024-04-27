package com.ubcompany.umbba_android.data.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CloserQuestionResponseDto(
    @SerialName("status")
    val status: Int,
    @SerialName("message")
    val message: String,
    @SerialName("data")
    val data: CloserData
) {
    @Serializable
    data class CloserData(
        @SerialName("closer_qna_id")
        val closerQnaId: Long,
        @SerialName("response_case")
        val responseCase: Int,
        @SerialName("balance_question")
        val balanceQuestion: String,
        @SerialName("choice_answer1")
        val choiceAnswer1: String,
        @SerialName("choice_answer2")
        val choiceAnswer2: String,
        @SerialName("my_choice")
        val myChoice: String?,
        @SerialName("opponent_choice")
        val opponentChoice:String?,
        @SerialName("img_url")
        val imgUrl : String?
    )
}
