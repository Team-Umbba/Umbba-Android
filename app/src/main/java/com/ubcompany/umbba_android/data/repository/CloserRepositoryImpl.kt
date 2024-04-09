package com.ubcompany.umbba_android.data.repository

import android.util.Log
import com.ubcompany.umbba_android.data.datasource.CloserRemoteDataSource
import com.ubcompany.umbba_android.data.model.request.CloserAnswerRequestDto
import com.ubcompany.umbba_android.data.model.response.CloserAnswerResponseDto
import com.ubcompany.umbba_android.data.model.response.CloserNextResponseDto
import com.ubcompany.umbba_android.data.model.response.CloserQuestionResponseDto
import com.ubcompany.umbba_android.domain.repository.CloserRepository
import javax.inject.Inject

class CloserRepositoryImpl @Inject constructor(
    private val closerRemoteDataSource : CloserRemoteDataSource
) : CloserRepository {

    override suspend fun getCloserQuestion() : Result<CloserQuestionResponseDto> =
        runCatching {
            closerRemoteDataSource.getCloserQuestion()
        }.onSuccess {
            Log.e("hyeon","get home data 성공")
        }.onFailure {
            Log.e("hyeon","get home data 성공")
        }

    override suspend fun answerCloserQuestion(answer : CloserAnswerRequestDto): Result<CloserAnswerResponseDto> =
        runCatching {
            closerRemoteDataSource.answerCloserQuestion(answer)
        }.onSuccess {
            Log.e("hyeon","answer data 성공")
        }.onFailure {
            Log.e("hyeon","answer data 실패")
        }

    override suspend fun getNextCloserQuestion(): Result<CloserNextResponseDto> =
        runCatching {
            closerRemoteDataSource.getNextCloserQuestion()
        }.onSuccess {
            Log.e("hyeon","get next data 성공")
        }.onFailure {
            Log.e("hyeon","get next data 실패 ")
        }
}