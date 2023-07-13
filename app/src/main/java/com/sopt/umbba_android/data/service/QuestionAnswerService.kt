package com.sopt.umbba_android.data.service

import com.sopt.umbba_android.data.model.request.AnswerRequestDto
import com.sopt.umbba_android.data.model.response.AnswerResponseDto
import com.sopt.umbba_android.data.model.response.QuestionAnswerResponseDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface QuestionAnswerService {
    @GET("/qna/today")
    suspend fun getQuestion() : QuestionAnswerResponseDto

    @POST("/qna/answer")
    suspend fun postAnswer(@Body answerRequestDto: AnswerRequestDto) : AnswerResponseDto
}