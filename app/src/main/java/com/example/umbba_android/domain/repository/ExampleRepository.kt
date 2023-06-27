package com.example.umbba_android.domain.repository

import com.example.umbba_android.data.model.request.ExampleRequestDto
import com.example.umbba_android.data.model.response.ExampleResponseDto
import com.example.umbba_android.data.model.response.wrapper.BaseResponse

interface ExampleRepository {
    suspend fun postExample(exampleRequestDto: ExampleRequestDto): Result<BaseResponse<ExampleResponseDto>>
}