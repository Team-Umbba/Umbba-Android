package com.ubcompany.umbba_android.data.datasource

import com.ubcompany.umbba_android.data.model.ServicePool
import com.ubcompany.umbba_android.data.service.HomeService
import javax.inject.Inject

class HomeRemoteDataSource @Inject constructor(
    private val homeService : HomeService
) {
    suspend fun getHomeData() = homeService.getHomeData()
    suspend fun getResponseCase() = homeService.getResponseCase()
}