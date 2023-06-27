package com.example.umbba_android.data.datasource

import com.example.umbba_android.data.service.ExampleService
import com.example.umbba_android.data.model.request.ExampleRequestDto
import com.example.umbba_android.data.model.response.ExampleResponseDto
import com.example.umbba_android.data.model.response.wrapper.BaseResponse

class ExampleDateSource(
    private val exampleService: ExampleService
) {
    suspend fun postExample(exampleRequestDto: ExampleRequestDto): BaseResponse<ExampleResponseDto> =
        exampleService.postExample(exampleRequestDto)
}