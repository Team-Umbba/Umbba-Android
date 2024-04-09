package com.ubcompany.umbba_android.data.datasource

import com.ubcompany.umbba_android.data.model.request.RecordImageRequestDto
import com.ubcompany.umbba_android.data.model.request.RecordUploadRequestDto
import com.ubcompany.umbba_android.data.service.SettingService
import javax.inject.Inject

class SettingRemoteDataSource @Inject constructor(
    private val settingService: SettingService
) {

    suspend fun getMypage() = settingService.getMypage()

    suspend fun uploadRecord(recordUploadRequestDto: RecordUploadRequestDto) =
        settingService.uploadRecord(recordUploadRequestDto)

    suspend fun getImageUrl(recordImageRequestDto: RecordImageRequestDto) =
        settingService.getImageUrl(recordImageRequestDto)

    suspend fun deleteRecord(albumId: Long) = settingService.deleteRecord(albumId)

    suspend fun getRecordList() = settingService.getRecordList()

    suspend fun logout() = settingService.logout()

    suspend fun signout() = settingService.signout()
}