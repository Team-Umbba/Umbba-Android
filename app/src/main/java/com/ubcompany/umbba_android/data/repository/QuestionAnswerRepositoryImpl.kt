package com.ubcompany.umbba_android.data.repository

import com.ubcompany.umbba_android.data.datasource.QuestionAnswerRemoteDataSource
import com.ubcompany.umbba_android.data.model.request.AnswerRequestDto
import com.ubcompany.umbba_android.data.model.response.AnswerResponseDto
import com.ubcompany.umbba_android.data.model.response.ListQuestionAnswerResponseDto
import com.ubcompany.umbba_android.data.model.response.QuestionAnswerResponseDto
import com.ubcompany.umbba_android.domain.repository.QuestionAnswerRepository
import timber.log.Timber
import javax.inject.Inject

class QuestionAnswerRepositoryImpl @Inject constructor(private val questionAnswerRemoteDataSource: QuestionAnswerRemoteDataSource) :
    QuestionAnswerRepository {
    override suspend fun getQuestionAnswer(): Result<QuestionAnswerResponseDto> =
        runCatching {
            questionAnswerRemoteDataSource.getQuestionAnswer()
        }.onSuccess {
            Timber.d("문답 data get 성공")
        }.onFailure {
            Timber.e("문답 data get 실패")
        }

    override suspend fun getListQuestionAnswer(qnaId: Long): Result<ListQuestionAnswerResponseDto> =
        runCatching {
            questionAnswerRemoteDataSource.getListQuestionAnswer(qnaId)
        }.onSuccess {
            Timber.d("list qna data get 성공")
        }.onFailure {
            Timber.e("list qna data get 실패")
        }

    override suspend fun postAnswer(answerRequestDto: AnswerRequestDto): Result<AnswerResponseDto> =
        runCatching {
            questionAnswerRemoteDataSource.postAnswer(answerRequestDto)
        }.onSuccess {
            Timber.d("답변 data post 성공")
        }.onFailure {
            Timber.e("답변 data post 실패")
        }
}