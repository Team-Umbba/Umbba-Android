package com.ubcompany.umbba_android.data.service

import com.ubcompany.umbba_android.data.model.request.RecordImageRequestDto
import com.ubcompany.umbba_android.data.model.request.RecordUploadRequestDto
import com.ubcompany.umbba_android.data.model.response.BaseResponseDto
import com.ubcompany.umbba_android.data.model.response.LogOutResponseDto
import com.ubcompany.umbba_android.data.model.response.MypageResponseDto
import com.ubcompany.umbba_android.data.model.response.RecordImageResponseDto
import com.ubcompany.umbba_android.data.model.response.RecordListResponseDto
import com.ubcompany.umbba_android.data.model.response.SignOutResponseDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface SettingService {
    @GET("/user/me")
    suspend fun getMypage(): MypageResponseDto

    @POST("/album")
    suspend fun uploadRecord(
        @Body body: RecordUploadRequestDto
    ): BaseResponseDto

    @PATCH("/album/image")
    suspend fun getImageUrl(
        @Body body: RecordImageRequestDto
    ): RecordImageResponseDto

    @DELETE("/album/{album_id}")
    suspend fun deleteRecord(
        @Path("album_id") albumId: Long
    ): BaseResponseDto

    @GET("/album")
    suspend fun getRecordList(): RecordListResponseDto

    @PATCH("/log-out")
    suspend fun logout(): LogOutResponseDto

    @PATCH("/sign-out")
    suspend fun signout(): SignOutResponseDto
}