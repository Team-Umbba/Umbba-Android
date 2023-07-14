package com.sopt.umbba_android.data.datasource

import com.sopt.umbba_android.data.model.ServicePool
import retrofit2.http.Header

class QuestionAnswerRemoteDataSource {
    private val questionAnswerService = ServicePool.questionAnswerService
    suspend fun getQuestionAnswer(token:String) = questionAnswerService.getQuestionAnswer(token)
}