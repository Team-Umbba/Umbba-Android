package com.example.umbba_android.data.repository

import com.example.umbba_android.data.datasource.ExampleDateSource
import com.example.umbba_android.data.model.request.ExampleRequestDto
import com.example.umbba_android.data.model.response.ExampleResponseDto
import com.example.umbba_android.data.model.response.wrapper.BaseResponse
import com.example.umbba_android.domain.repository.ExampleRepository

class ExampleRepositoryImpl(
    private val exampleDateSource: ExampleDateSource
) : ExampleRepository {
    override suspend fun postExample(exampleRequestDto: ExampleRequestDto): Result<BaseResponse<ExampleResponseDto>> =
        kotlin.runCatching { exampleDateSource.postExample(exampleRequestDto) }
}