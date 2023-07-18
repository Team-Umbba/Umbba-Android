package com.sopt.umbba_android.data.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponseDto(
    @SerialName("status")
    val status: Int,
    @SerialName("message")
    val message: String,
    @SerialName("data")
    val data: LoginData
) {
    @Serializable
    data class LoginData(
        @SerialName("user_id")
        val userId: Long,
        @SerialName("is_new_user")
        val isNewUser: Boolean,
        @SerialName("username")
        val username: String?,
        @SerialName("gender")
        val gender: String?,
        @SerialName("born_year")
        val bornYear: Int?,
        @SerialName("token_dto")
        val tokenDto: TokenData,
        @SerialName("fcm_token")
        val fcmToken: String,
        @SerialName("social_platform")
        val socialPlatform: String,
        @SerialName("social_nickname")
        val socialNickname: String,
        @SerialName("social_profile_image")
        val socialProfileImage: String,
        @SerialName("social_access_token")
        val socialAccessToken: String
    ) {
        @Serializable
        data class TokenData(
            @SerialName("access_token")
            val accessToken: String,
            @SerialName("refresh_token")
            val refreshToken: String
        )
    }
}
