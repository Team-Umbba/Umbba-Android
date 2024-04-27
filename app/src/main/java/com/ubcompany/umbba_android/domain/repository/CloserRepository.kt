package com.ubcompany.umbba_android.domain.repository

import com.ubcompany.umbba_android.data.model.request.CloserAnswerRequestDto
import com.ubcompany.umbba_android.data.model.response.CloserAnswerResponseDto
import com.ubcompany.umbba_android.data.model.response.CloserNextResponseDto
import com.ubcompany.umbba_android.data.model.response.CloserQuestionResponseDto

interface CloserRepository {
    suspend fun getCloserQuestion() : Result<CloserQuestionResponseDto>
    suspend fun answerCloserQuestion(answer : CloserAnswerRequestDto) : Result<CloserAnswerResponseDto>
    suspend fun getNextCloserQuestion() : Result<CloserNextResponseDto>
}