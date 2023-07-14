package com.sopt.umbba_android.data.service

import com.sopt.umbba_android.data.model.response.HomeResponseDto
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers

interface HomeService {
    @Headers("Content-Type: application/json")
    @GET("/home")
    suspend fun getHomeData(): HomeResponseDto
}