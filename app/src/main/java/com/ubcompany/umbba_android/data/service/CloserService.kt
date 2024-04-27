package com.ubcompany.umbba_android.data.service

import com.ubcompany.umbba_android.data.model.request.CloserAnswerRequestDto
import com.ubcompany.umbba_android.data.model.response.CloserAnswerResponseDto
import com.ubcompany.umbba_android.data.model.response.CloserNextResponseDto
import com.ubcompany.umbba_android.data.model.response.CloserQuestionResponseDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH

interface CloserService {
    @GET ("/closer/today")
    suspend fun getCloserQuestion() : CloserQuestionResponseDto

    @PATCH("/closer/answer")
    suspend fun answerCloserQuestion(@Body answer:CloserAnswerRequestDto) : CloserAnswerResponseDto

    @PATCH("/closer/next")
    suspend fun getNextCloserQuestion() : CloserNextResponseDto
}