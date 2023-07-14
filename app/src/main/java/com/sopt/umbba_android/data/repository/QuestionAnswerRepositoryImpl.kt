package com.sopt.umbba_android.data.repository

import com.sopt.umbba_android.data.datasource.QuestionAnswerRemoteDataSource
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
    override suspend fun postAnswer() {
        TODO("Not yet implemented")
    }
}