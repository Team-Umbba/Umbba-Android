package com.ubcompany.umbba_android.data.datasource

import com.ubcompany.umbba_android.data.model.ServicePool

class HomeRemoteDataSource {
    private val homeService = ServicePool.homeService
    suspend fun getHomeData() = homeService.getHomeData()
    suspend fun getResponseCase() = homeService.getResponseCase()
}