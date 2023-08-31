package com.ubcompany.umbba_android.data.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginRequestDto(
    @SerialName("social_platform")
    val socialPlatform: String,
    @SerialName("fcm_token")
    val fcmToken: String
)