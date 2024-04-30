package com.ubcompany.umbba_android.data.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HomeFirstResponseDto (
    @SerialName("status")
    val status: Int,
    @SerialName("message")
    val message: String,
    @SerialName("data")
    val data: HomeFirstData
) {
    @Serializable
    data class HomeFirstData(
        @SerialName("is_first_entry")
        val isFirstEntry: Boolean
    )
}