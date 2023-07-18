package com.sopt.umbba_android.data.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class InviteCodeResponseDto(
    @SerialName("status")
    val status: Int,
    @SerialName("message")
    val message: String,
    @SerialName("data")
    val data: ParentChildData
) {
    @Serializable
    data class ParentChildData(
        @SerialName("parentchild_id")
        val parentChildId: Long,
        @SerialName("parentchild_users")
        val parentChildUsers: List<UsersData>,
        @SerialName("parentchild_relation")
        val parentChildRelation: String
    ) {
        @Serializable
        data class UsersData(
            @SerialName("user_id")
            val userId: Long,
            @SerialName("name")
            val name: String?,
            @SerialName("gender")
            val gender: String?,
            @SerialName("born_year")
            val bornYear: Int?,
            @SerialName("is_me_child")
            val isMeChild: Boolean?
        )
    }
}
