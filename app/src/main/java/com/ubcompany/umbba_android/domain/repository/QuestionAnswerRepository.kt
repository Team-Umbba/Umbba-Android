package com.ubcompany.umbba_android.domain.repository

import com.ubcompany.umbba_android.data.model.request.AnswerRequestDto
import com.ubcompany.umbba_android.data.model.response.AnswerResponseDto
import com.ubcompany.umbba_android.data.model.response.ListQuestionAnswerResponseDto
import com.ubcompany.umbba_android.data.model.response.QuestionAnswerResponseDto

interface QuestionAnswerRepository {
    suspend fun getQuestionAnswer(): Result<QuestionAnswerResponseDto>
    suspend fun getListQuestionAnswer(qnaId: Long): Result<ListQuestionAnswerResponseDto>
    suspend fun postAnswer(answerRequestDto: AnswerRequestDto): Result<AnswerResponseDto>
}