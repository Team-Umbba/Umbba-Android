package com.sopt.umbba_android.data.datasource

import com.sopt.umbba_android.data.model.ServicePool

class HomeRemoteDataSource {
    private val homeService = ServicePool.homeService
    suspend fun getHomeData() = homeService.getHomeData()
}