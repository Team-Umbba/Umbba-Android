package com.ubcompany.umbba_android.domain.repository

import com.ubcompany.umbba_android.data.model.response.HomeCaseResponseDto
import com.ubcompany.umbba_android.data.model.response.HomeFirstResponseDto
import com.ubcompany.umbba_android.data.model.response.HomeResponseDto

interface HomeRepository {
    suspend fun getHomeData(): Result<HomeResponseDto>
    suspend fun getResponseCase(): Result<HomeCaseResponseDto>

    suspend fun patchHomeFirst() : Result<HomeFirstResponseDto>
}