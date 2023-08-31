package com.ubcompany.umbba_android.data.repository

import com.ubcompany.umbba_android.data.datasource.HomeRemoteDataSource
import com.ubcompany.umbba_android.data.model.response.HomeCaseResponseDto
import com.ubcompany.umbba_android.data.model.response.HomeResponseDto
import com.ubcompany.umbba_android.domain.repository.HomeRepository
import timber.log.Timber

class HomeRepositoryImpl(private val homeRemoteDataSource: HomeRemoteDataSource) : HomeRepository {
    override suspend fun getHomeData(): Result<HomeResponseDto> =
        runCatching {
            homeRemoteDataSource.getHomeData()
        }.onSuccess {
            Timber.d("get home data 성공")
        }.onFailure {
            Timber.e("get home data 실패")
        }

    override suspend fun getResponseCase(): Result<HomeCaseResponseDto> =
        runCatching {
            homeRemoteDataSource.getResponseCase()
        }.onSuccess {
            Timber.d("get response case 성공")
        }.onFailure {
            Timber.e("get response case 실패")
        }
}