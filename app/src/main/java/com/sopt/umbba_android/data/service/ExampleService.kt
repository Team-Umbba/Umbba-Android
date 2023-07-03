package com.sopt.umbba_android.data.service

import com.sopt.umbba_android.data.model.request.ExampleRequestDto
import com.sopt.umbba_android.data.model.response.ExampleResponseDto
import com.sopt.umbba_android.data.model.response.wrapper.BaseResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ExampleService {
    // 예시 API
    @POST("api/example")
    suspend fun postExample(
        @Body request: ExampleRequestDto
    ): BaseResponse<ExampleResponseDto>
}