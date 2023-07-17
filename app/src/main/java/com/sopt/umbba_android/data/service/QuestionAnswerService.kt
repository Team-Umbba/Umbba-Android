package com.sopt.umbba_android.data.service

import com.sopt.umbba_android.data.model.request.AnswerRequestDto
import com.sopt.umbba_android.data.model.response.AnswerResponseDto
import com.sopt.umbba_android.data.model.response.ListQuestionAnswerResponseDto
import com.sopt.umbba_android.data.model.response.QuestionAnswerResponseDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface QuestionAnswerService {
    @GET("/qna/today")
    suspend fun getQuestionAnswer(): QuestionAnswerResponseDto

    @GET("/qna/{qnaId}")
    suspend fun getListQuestionAnswer(@Path("qnaId") qnaId: Long): ListQuestionAnswerResponseDto

    @POST("/qna/answer")
    suspend fun postAnswer(@Body answerRequestDto: AnswerRequestDto): AnswerResponseDto
}