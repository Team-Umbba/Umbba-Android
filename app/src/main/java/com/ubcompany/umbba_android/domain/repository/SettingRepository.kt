package com.ubcompany.umbba_android.domain.repository

import com.ubcompany.umbba_android.data.model.request.RecordImageRequestDto
import com.ubcompany.umbba_android.data.model.request.RecordUploadRequestDto
import com.ubcompany.umbba_android.data.model.response.BaseResponseDto
import com.ubcompany.umbba_android.data.model.response.LogOutResponseDto
import com.ubcompany.umbba_android.data.model.response.MypageResponseDto
import com.ubcompany.umbba_android.data.model.response.RecordImageResponseDto
import com.ubcompany.umbba_android.data.model.response.RecordListResponseDto
import com.ubcompany.umbba_android.data.model.response.SignOutResponseDto

interface SettingRepository {

    suspend fun getMypage(): Result<MypageResponseDto>
    suspend fun uploadRecord(recordUploadRequestDto: RecordUploadRequestDto): Result<BaseResponseDto>
    suspend fun getImageUrl(recordImageRequestDto: RecordImageRequestDto): Result<RecordImageResponseDto>
    suspend fun deleteRecord(albumId: Long): Result<BaseResponseDto>
    suspend fun getRecordList(): Result<RecordListResponseDto>
    suspend fun logout(): Result<LogOutResponseDto>
    suspend fun signout(): Result<SignOutResponseDto>
}