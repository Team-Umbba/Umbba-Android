package com.sopt.umbba_android.domain.repository

import com.sopt.umbba_android.data.model.response.QuestionAnswerResponseDto

interface QuestionAnswerRepository {
    suspend fun getQuestionAnswer(token:String): Result<QuestionAnswerResponseDto>
    suspend fun postAnswer()
}