package com.ubcompany.umbba_android.data.datasource

import com.ubcompany.umbba_android.data.model.request.AnswerRequestDto
import com.ubcompany.umbba_android.data.model.request.RefreshQuestionRequestDto
import com.ubcompany.umbba_android.data.service.QuestionAnswerService
import javax.inject.Inject

class QuestionAnswerRemoteDataSource @Inject constructor(
    private val questionAnswerService: QuestionAnswerService
) {
    suspend fun getQuestionAnswer() = questionAnswerService.getQuestionAnswer()
    suspend fun getListQuestionAnswer(qnaId: Long) =
        questionAnswerService.getListQuestionAnswer(qnaId)

    suspend fun postAnswer(answerRequestDto: AnswerRequestDto) =
        questionAnswerService.postAnswer(answerRequestDto)

    suspend fun getRefreshQuestion() = questionAnswerService.getRefreshQuestion()

    suspend fun patchRefreshQuestion(refreshQuestionRequestDto: RefreshQuestionRequestDto) =
        questionAnswerService.patchRefreshQuestion(refreshQuestionRequestDto)
}