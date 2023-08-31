package com.sopt.umbba_android.data.service

import com.sopt.umbba_android.data.model.response.HomeCaseResponseDto
import com.sopt.umbba_android.data.model.response.HomeResponseDto
import retrofit2.http.GET

interface HomeService {
    @GET("/home")
    suspend fun getHomeData(): HomeResponseDto

    @GET("/home/case")
    suspend fun getResponseCase(): HomeCaseResponseDto
}