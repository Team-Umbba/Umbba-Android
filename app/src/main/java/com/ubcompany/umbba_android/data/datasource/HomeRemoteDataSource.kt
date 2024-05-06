package com.ubcompany.umbba_android.data.datasource

import com.ubcompany.umbba_android.data.service.HomeService
import javax.inject.Inject

class HomeRemoteDataSource @Inject constructor(
    private val homeService : HomeService
) {
    suspend fun getHomeData() = homeService.getHomeData()
    suspend fun getResponseCase() = homeService.getResponseCase()
    suspend fun patchHomeFirst() = homeService.patchHomeFirst()
    suspend fun patch7DaysAfter() = homeService.patch7DaysAfter()
}