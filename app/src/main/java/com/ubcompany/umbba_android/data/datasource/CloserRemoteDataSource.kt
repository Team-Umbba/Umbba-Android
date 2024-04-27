package com.ubcompany.umbba_android.data.datasource

import com.ubcompany.umbba_android.data.model.request.CloserAnswerRequestDto
import com.ubcompany.umbba_android.data.service.CloserService
import javax.inject.Inject

class CloserRemoteDataSource @Inject constructor(private val closerService : CloserService) {

    suspend fun getCloserQuestion() = closerService.getCloserQuestion()

    suspend fun answerCloserQuestion(answer : CloserAnswerRequestDto) = closerService.answerCloserQuestion(answer)

    suspend fun getNextCloserQuestion() = closerService.getNextCloserQuestion()
}