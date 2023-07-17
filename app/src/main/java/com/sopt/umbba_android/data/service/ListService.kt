package com.sopt.umbba_android.data.service

import com.sopt.umbba_android.data.model.response.ListResponseDto
import retrofit2.http.GET
import retrofit2.http.Path

interface ListService {
    @GET("qna/list/{sectionId}")
    suspend fun getListData(@Path("sectionId") sectionId:Int):ListResponseDto
}