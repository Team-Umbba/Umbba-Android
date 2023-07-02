package com.sopt.umbba_android.data.model.response

import kotlinx.serialization.Serializable

@Serializable
data class ExampleResponseDto(
    val id: Int,
    val title: String,
    val content: String
)