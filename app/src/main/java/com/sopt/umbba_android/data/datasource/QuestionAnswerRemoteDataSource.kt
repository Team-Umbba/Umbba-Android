package com.sopt.umbba_android.data.datasource

import com.sopt.umbba_android.data.model.ServicePool

class QuestionAnswerRemoteDataSource {
    private val questionAnswerService = ServicePool.questionAnswerService
    suspend fun getQuestionAnswer() = questionAnswerService.getQuestionAnswer()
}