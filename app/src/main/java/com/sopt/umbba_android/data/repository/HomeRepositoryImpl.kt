package com.sopt.umbba_android.data.repository

import android.util.Log
import com.sopt.umbba_android.data.datasource.HomeRemoteDataSource
import com.sopt.umbba_android.data.model.response.HomeResponseDto
import com.sopt.umbba_android.domain.repository.HomeRepository
import timber.log.Timber

class HomeRepositoryImpl(private val homeRemoteDataSource: HomeRemoteDataSource):HomeRepository {
    override suspend fun getHomeData(): Result<HomeResponseDto> =
        runCatching {
            homeRemoteDataSource.getHomeData()
        }.onSuccess {
            Timber.e("get home data 성공~")
        }.onFailure {
            Timber.e("get home data 실패")
        }
}