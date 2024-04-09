package com.ubcompany.umbba_android.data.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RecordUploadRequestDto (
    @SerialName("title")
    val title: String,
    @SerialName("content")
    val content: String,
    @SerialName("img_file_name")
    val imgName: String
)