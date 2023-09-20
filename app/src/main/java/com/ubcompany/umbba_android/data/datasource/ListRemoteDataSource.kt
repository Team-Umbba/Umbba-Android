package com.ubcompany.umbba_android.data.datasource

import com.ubcompany.umbba_android.data.service.ListService
import javax.inject.Inject

class ListRemoteDataSource @Inject constructor(
    private val listService: ListService
) {
    suspend fun getListData(sectionId: Int) = listService.getListData(sectionId)
}