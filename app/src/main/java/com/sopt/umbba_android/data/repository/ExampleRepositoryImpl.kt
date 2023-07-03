package com.sopt.umbba_android.data.repository

import com.sopt.umbba_android.data.datasource.ExampleDateSource
import com.sopt.umbba_android.data.model.request.ExampleRequestDto
import com.sopt.umbba_android.data.model.response.ExampleResponseDto
import com.sopt.umbba_android.data.model.response.wrapper.BaseResponse
import com.sopt.umbba_android.domain.repository.ExampleRepository

class ExampleRepositoryImpl(
    private val exampleDateSource: ExampleDateSource
) : ExampleRepository {
    override suspend fun postExample(exampleRequestDto: ExampleRequestDto): Result<BaseResponse<ExampleResponseDto>> =
        kotlin.runCatching { exampleDateSource.postExample(exampleRequestDto) }
}