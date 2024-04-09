package com.ubcompany.umbba_android.data.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MypageResponseDto(
    @SerialName("status")
    val status: Int,
    @SerialName("message")
    val message: String,
    @SerialName("data")
    val data: MypageData
) {
    @Serializable
    data class MypageData(
        @SerialName("my_username")
        val myUserName: String,
        @SerialName("my_user_type")
        val myUserType: String,
        @SerialName("opponent_username")
        val opponentUsername: String?,
        @SerialName("opponent_user_type")
        val opponentUserType: String,
        @SerialName("parentchild_relation")
        val relation: String,
        @SerialName("is_me_child")
        val isMeChild: Boolean,
        @SerialName("section")
        val section: String,
        @SerialName("matched_date")
        val matchedDate: Long,
        @SerialName("qna_cnt")
        val qnaCount: Int,
        @SerialName("invite_code")
        val inviteCode: String?,
        @SerialName("install_url")
        val url: String?
    )
}
