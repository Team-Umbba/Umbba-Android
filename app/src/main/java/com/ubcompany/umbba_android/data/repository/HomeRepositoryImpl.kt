package com.ubcompany.umbba_android.data.repository

import android.util.Log
import com.ubcompany.umbba_android.data.datasource.HomeRemoteDataSource
import com.ubcompany.umbba_android.data.model.response.HomeCaseResponseDto
import com.ubcompany.umbba_android.data.model.response.HomeResponseDto
import com.ubcompany.umbba_android.domain.repository.HomeRepository
import retrofit2.HttpException
import timber.log.Timber
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val homeRemoteDataSource: HomeRemoteDataSource
) : HomeRepository {
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
        }.onFailure {error ->
            Timber.e("get response case 실패")
            if(error is HttpException){
                val errorBody = error.response()?.errorBody()?.string()
                Log.e("hyeon","겟 실패 ${errorBody}")
            }
            Log.e("hyeon", "getresponse 실패 ${error.message} ")
        }
}