package com.ubcompany.umbba_android.data.service

import com.ubcompany.umbba_android.data.model.response.ListResponseDto
import retrofit2.http.GET
import retrofit2.http.Path

interface ListService {
    @GET("qna/list/{sectionId}")
    suspend fun getListData(@Path("sectionId") sectionId: Int): ListResponseDto
}