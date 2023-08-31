package com.sopt.umbba_android.domain.repository

import com.sopt.umbba_android.data.model.request.AnswerRequestDto
import com.sopt.umbba_android.data.model.response.AnswerResponseDto
import com.sopt.umbba_android.data.model.response.ListQuestionAnswerResponseDto
import com.sopt.umbba_android.data.model.response.QuestionAnswerResponseDto

interface QuestionAnswerRepository {
    suspend fun getQuestionAnswer(): Result<QuestionAnswerResponseDto>
    suspend fun getListQuestionAnswer(qnaId: Long): Result<ListQuestionAnswerResponseDto>
    suspend fun postAnswer(answerRequestDto: AnswerRequestDto): Result<AnswerResponseDto>
}