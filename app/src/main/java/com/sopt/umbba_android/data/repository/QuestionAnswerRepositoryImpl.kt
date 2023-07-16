package com.sopt.umbba_android.data.repository

import com.sopt.umbba_android.data.datasource.QuestionAnswerRemoteDataSource
import com.sopt.umbba_android.data.model.request.AnswerRequestDto
import com.sopt.umbba_android.data.model.response.AnswerResponseDto
import com.sopt.umbba_android.data.model.response.ListQuestionAnswerResponseDto
import com.sopt.umbba_android.data.model.response.QuestionAnswerResponseDto
import com.sopt.umbba_android.domain.repository.QuestionAnswerRepository
import timber.log.Timber

class QuestionAnswerRepositoryImpl(private val questionAnswerRemoteDataSource: QuestionAnswerRemoteDataSource) :
    QuestionAnswerRepository {
    override suspend fun getQuestionAnswer(): Result<QuestionAnswerResponseDto> =
        runCatching {
            questionAnswerRemoteDataSource.getQuestionAnswer()
        }.onSuccess {
            Timber.e("문답 data get 성공")
        }.onFailure {
            Timber.e("문답 data get 실패 크라잉. . . ")
        }

    override suspend fun getListQuestionAnswer(qnaId: Long): Result<ListQuestionAnswerResponseDto> =
        runCatching {
            questionAnswerRemoteDataSource.getListQuestionAnswer(qnaId)
        }.onSuccess {
            Timber.e("list qna data get 성공")
        }.onFailure {
            Timber.e("list qna data get 시이일패")
        }

    override suspend fun postAnswer(answerRequestDto: AnswerRequestDto):Result<AnswerResponseDto> =
        runCatching {
            questionAnswerRemoteDataSource.postAnswer(answerRequestDto)
        }.onSuccess {
            Timber.e("답변 data post 성공")
        }.onFailure {
            Timber.e("답변 data post 실패 크라잉. . . ")
        }
}