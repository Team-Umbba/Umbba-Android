package com.ubcompany.umbba_android.data.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReceiveInfoResponseDto(
    @SerialName("status")
    val status: Int,
    @SerialName("message")
    val message: String,
    @SerialName("data")
    val data: Data,
)

@Serializable
data class Data(
    @SerialName("user_info")
    val userInfo: UserInfo,
    @SerialName("parentchild_info")
    val parentChildInfo: ParentChildInfo,
    @SerialName("push_time")
    val pushTime: String,
)

@Serializable
data class UserInfo(
    @SerialName("user_id")
    val userId: Long,
    @SerialName("name")
    val name: String,
    @SerialName("gender")
    val gender: String,
    @SerialName("born_year")
    val bornYear: Int,
    @SerialName("is_me_child")
    val isMeChild: Boolean,
)

@Serializable
data class ParentChildInfo(
    @SerialName("parentchild_id")
    val parentChildId: Long,
    @SerialName("parentchild_users")
    val parentChildUsers: List<ParentChildUser>,
    @SerialName("parentchild_relation")
    val parentChildRelation: String,
)

@Serializable
data class ParentChildUser(
    @SerialName("user_id")
    val userId: Long,
    @SerialName("name")
    val name: String,
    @SerialName("gender")
    val gender: String,
    @SerialName("born_year")
    val bornYear: Int,
    @SerialName("is_me_child")
    val isMeChild: Boolean,
)