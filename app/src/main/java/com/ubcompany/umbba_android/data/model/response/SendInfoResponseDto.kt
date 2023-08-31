package com.ubcompany.umbba_android.data.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SendInfoResponseDto(
    @SerialName("status")
    val status: Int,
    @SerialName("message")
    val message: String,
    @SerialName("data")
    val data: Data
) {
    @Serializable
    data class Data(
        @SerialName("parentchild_id")
        val parentChildId: Long,
        @SerialName("user_info")
        val userInfo: UserData,
        @SerialName("parentchild_relation")
        val parentChildRelation: String,
        @SerialName("push_time")
        val pushTime: String,
        @SerialName("invite_code")
        val inviteCode: String
    ) {
        @Serializable
        data class UserData(
            @SerialName("user_id")
            val userId: Long,
            @SerialName("name")
            val name: String,
            @SerialName("gender")
            val gender: String,
            @SerialName("born_year")
            val bornYear: Int,
            @SerialName("is_me_child")
            val isMeChild: Boolean
        )
    }
}