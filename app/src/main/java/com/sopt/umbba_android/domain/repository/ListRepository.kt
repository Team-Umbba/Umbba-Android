package com.sopt.umbba_android.domain.repository

import com.sopt.umbba_android.data.model.response.ListResponseDto

interface ListRepository {
    suspend fun getListData(sectionId: Int): Result<ListResponseDto>
}