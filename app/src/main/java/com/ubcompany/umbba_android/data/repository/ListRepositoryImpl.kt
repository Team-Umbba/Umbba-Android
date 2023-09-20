package com.ubcompany.umbba_android.data.repository

import com.ubcompany.umbba_android.data.datasource.ListRemoteDataSource
import com.ubcompany.umbba_android.data.model.response.ListResponseDto
import com.ubcompany.umbba_android.domain.repository.ListRepository
import timber.log.Timber
import javax.inject.Inject

class ListRepositoryImpl@Inject constructor(
    private val listRemoteDataSource: ListRemoteDataSource
)  : ListRepository {
    override suspend fun getListData(sectionId: Int): Result<ListResponseDto> =
        runCatching {
            listRemoteDataSource.getListData(sectionId)
        }.onSuccess {
            Timber.d("get list data 성공")
        }.onFailure {
            Timber.e("get list data 실패")
        }
}