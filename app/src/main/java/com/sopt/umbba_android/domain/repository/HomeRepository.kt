package com.sopt.umbba_android.domain.repository

import com.sopt.umbba_android.data.model.response.HomeResponseDto

interface HomeRepository {
    suspend fun getHomeData():Result<HomeResponseDto>
}