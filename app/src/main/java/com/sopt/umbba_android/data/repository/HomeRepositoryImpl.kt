package com.sopt.umbba_android.data.repository

import android.util.Log
import com.sopt.umbba_android.data.datasource.HomeRemoteDataSource
import com.sopt.umbba_android.data.model.response.HomeResponseDto
import com.sopt.umbba_android.domain.repository.HomeRepository

class HomeRepositoryImpl(private val homeRemoteDataSource: HomeRemoteDataSource):HomeRepository {
    override suspend fun getHomeData(): Result<HomeResponseDto> =
        runCatching {
            homeRemoteDataSource.getHomeData()
        }.onSuccess {
            Log.e("hyeon","get home data 성공~")
        }.onFailure {
            Log.e("hyeon","get home data 실패")
        }
}