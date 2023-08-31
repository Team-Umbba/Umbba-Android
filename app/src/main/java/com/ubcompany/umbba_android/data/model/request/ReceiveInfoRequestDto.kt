package com.ubcompany.umbba_android.data.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReceiveInfoRequestDto(
    @SerialName("user_info")
    val userInfo: UserInfoData,
    @SerialName("onboarding_answer_list")
    val onboardingAnswerList: List<String>
) {
    @Serializable
    data class UserInfoData(
        @SerialName("name")
        val name: String,
        @SerialName("gender")
        val gender: String,
        @SerialName("born_year")
        val bornYear: Int
    )
}