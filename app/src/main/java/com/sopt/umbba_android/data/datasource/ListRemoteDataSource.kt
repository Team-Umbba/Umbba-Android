package com.sopt.umbba_android.data.datasource

import com.sopt.umbba_android.data.model.ServicePool

class ListRemoteDataSource {
    private val listService = ServicePool.listService
    suspend fun getListData(sectionId: Int) = listService.getListData(sectionId)
}