package com.sopt.umbba_android.data.datasource

import com.sopt.umbba_android.data.model.ServicePool
import com.sopt.umbba_android.data.model.request.AnswerRequestDto
import retrofit2.http.Header

class QuestionAnswerRemoteDataSource {
    private val questionAnswerService = ServicePool.questionAnswerService
    suspend fun getQuestionAnswer() = questionAnswerService.getQuestionAnswer()
    suspend fun getListQuestionAnswer(qnaId:Long) = questionAnswerService.getListQuestionAnswer(qnaId)
    suspend fun postAnswer(answerRequestDto : AnswerRequestDto) = questionAnswerService.postAnswer(answerRequestDto)
}