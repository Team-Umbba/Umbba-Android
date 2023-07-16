package com.sopt.umbba_android.data.repository

import com.sopt.umbba_android.data.datasource.ListRemoteDataSource
import com.sopt.umbba_android.data.model.response.ListResponseDto
import com.sopt.umbba_android.domain.repository.ListRepository
import timber.log.Timber

class ListRepositoryImpl(private val listRemoteDataSource: ListRemoteDataSource) : ListRepository {
    override suspend fun getListData(sectionId: Int): Result<ListResponseDto> =
        runCatching {
            listRemoteDataSource.getListData(sectionId)
        }.onSuccess {
            Timber.e("get list data 성공~")
        }.onFailure {
            Timber.e("get list data 실패 크라잉 . . . ")
        }
}