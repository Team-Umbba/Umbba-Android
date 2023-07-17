package com.sopt.umbba_android.data.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HomeCaseResponseDto(
    @SerialName("status")
    val status: Int,
    @SerialName("message")
    val message: String,
    @SerialName("data")
    val data: HomeCaseData
) {
    @Serializable
    data class HomeCaseData(
        @SerialName("response_case")
        val responseCase: Int,
        @SerialName("invite_code")
        val inviteCode: String?,
        @SerialName("invite_username")
        val inviteUserName: String?,
        @SerialName("install_url")
        val installUrl: String?,
        @SerialName("relative_user_active")
        val relativeUserActive: Boolean?
    )
}
