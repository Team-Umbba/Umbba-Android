package com.ubcompany.umbba_android.data.repository

import android.graphics.Bitmap
import com.ubcompany.umbba_android.data.datasource.SettingRemoteDataSource
import com.ubcompany.umbba_android.data.model.request.RecordImageRequestDto
import com.ubcompany.umbba_android.data.model.request.RecordUploadRequestDto
import com.ubcompany.umbba_android.data.model.response.BaseResponseDto
import com.ubcompany.umbba_android.data.model.response.LogOutResponseDto
import com.ubcompany.umbba_android.data.model.response.MypageResponseDto
import com.ubcompany.umbba_android.data.model.response.RecordImageResponseDto
import com.ubcompany.umbba_android.data.model.response.RecordListResponseDto
import com.ubcompany.umbba_android.data.model.response.SignOutResponseDto
import com.ubcompany.umbba_android.domain.repository.SettingRepository
import com.ubcompany.umbba_android.util.BitmapRequestBody
import timber.log.Timber
import javax.inject.Inject

class SettingRepositoryImpl @Inject constructor(private val settingRemoteDataSource: SettingRemoteDataSource) :
    SettingRepository {

    override suspend fun getMypage(): Result<MypageResponseDto> =
        runCatching {
            settingRemoteDataSource.getMypage()
        }.onSuccess {
            Timber.d("마이페이지 data get 성공")
        }.onFailure {
            Timber.e("마이페이지 data get 실패")
        }

    override suspend fun uploadRecord(recordUploadRequestDto: RecordUploadRequestDto): Result<BaseResponseDto> =
        runCatching {
            settingRemoteDataSource.uploadRecord(recordUploadRequestDto)
        }.onSuccess {
            Timber.d("기록 post 성공")
        }.onFailure {
            Timber.e("기록 post 실패")
        }

    override suspend fun getImageUrl(recordImageRequestDto: RecordImageRequestDto): Result<RecordImageResponseDto> =
        runCatching {
            settingRemoteDataSource.getImageUrl(recordImageRequestDto)
        }.onSuccess {
            Timber.d("이미지 url patch 성공")
        }.onFailure {
            Timber.e("이미지 url patch 실패")
        }

    override suspend fun uploadImage(url: String, bitmap: Bitmap) {
        val requestBody = BitmapRequestBody(bitmap).create()
        runCatching {
            settingRemoteDataSource.uploadImage(url, requestBody)
        }.onSuccess {
            Timber.d("이미지 upload 성공")
        }.onFailure {
            Timber.e("이미지 upload 실패")
        }
    }

    override suspend fun deleteRecord(albumId: Long): Result<BaseResponseDto> =
        runCatching {
            settingRemoteDataSource.deleteRecord(albumId)
        }.onSuccess {
            Timber.d("기록 delete 성공")
        }.onFailure {
            Timber.e("기록 delete 실패")
        }

    override suspend fun getRecordList(): Result<RecordListResponseDto> =
        runCatching {
            settingRemoteDataSource.getRecordList()
        }.onSuccess {
            Timber.d("기록 리스트 get 성공")
        }.onFailure {
            Timber.e("기록 리스트 get 실패")
        }

    override suspend fun logout(): Result<LogOutResponseDto> =
        runCatching {
            settingRemoteDataSource.logout()
        }.onSuccess {
            Timber.d("Impl 로그아웃 성공")
        }.onFailure {
            Timber.e("Impl 로그아웃 실패")
        }

    override suspend fun signout(): Result<SignOutResponseDto> =
        runCatching {
            settingRemoteDataSource.signout()
        }.onSuccess {
            Timber.d("Impl 회원탈퇴 성공")
        }.onFailure {
            Timber.e("Impl 회원탈퇴 실패")
        }
}