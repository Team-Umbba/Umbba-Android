package com.sopt.umbba_android.data.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class InviteCodeRequestDto(
    @SerialName("invite_code")
    val inviteCode: String
)
