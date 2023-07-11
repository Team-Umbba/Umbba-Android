package com.sopt.umbba_android.presentation.qna

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sopt.umbba_android.data.model.response.QuestionAnswerResponseDto

class QuestionAnswerViewModel : ViewModel() {
    private val _qnaResponse = MutableLiveData<QuestionAnswerResponseDto>()
    val qnaResponse: LiveData<QuestionAnswerResponseDto> = _qnaResponse
}