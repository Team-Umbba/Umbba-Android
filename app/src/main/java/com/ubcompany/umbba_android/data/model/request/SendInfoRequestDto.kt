package com.ubcompany.umbba_android.data.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SendInfoRequestDto(
    @SerialName("user_info")
    val userInfo: SenderData,
    @SerialName("is_invitor_child")
    val isInvitorChild: Boolean,
    @SerialName("relation_info")
    val relationInfo: String,
    @SerialName("push_time")
    val pushTime: String,
    @SerialName("onboarding_answer_list")
    val onboardingAnswerList: List<String>

) {
    @Serializable
    data class SenderData(
        @SerialName("name")
        val name: String,
        @SerialName("gender")
        val gender: String,
        @SerialName("born_year")
        val bornYear: Int
    )
}
