package com.sopt.umbba_android.presentation.qna

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.umbba_android.data.model.response.QuestionAnswerResponseDto
import com.sopt.umbba_android.data.repository.QuestionAnswerRepositoryImpl
import kotlinx.coroutines.launch
import timber.log.Timber

class QuestionAnswerViewModel(private val questionAnswerRepositoryImpl: QuestionAnswerRepositoryImpl) :
    ViewModel() {
    private val token =
        "Bearer "+"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJpYXQiOjE2ODkzNTAyNzQsImV4cCI6MTY4OTM1Mzg3NCwidXNlcklkIjoxfQ.uzkMh9GmBOlM6Dy3J5r2Edjpw7Sm3jPUSZGQYQpNFWYfFYZJgAqQ4wlkaye8clg7SQmPbSOnkvaj5D64I22qQA"

    private val contentType = "application/json"
    private var _qnaResponse = MutableLiveData<QuestionAnswerResponseDto.QnaData>()
    val qnaResponse: LiveData<QuestionAnswerResponseDto.QnaData> = _qnaResponse

    fun getQuestionAnswer() {
        viewModelScope.launch {
            questionAnswerRepositoryImpl.getQuestionAnswer(token)
                .onSuccess { response ->
                    Log.e("hyeon", "getQuestionAnswer 성공")
                    _qnaResponse.value = response.data
                }.onFailure { error ->
                Log.e("hyeon", "getQuestionAnswer 실패  " + error.message)
            }
        }
    }
}