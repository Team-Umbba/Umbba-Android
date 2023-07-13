package com.sopt.umbba_android.presentation.qna

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sopt.umbba_android.data.model.response.AnswerResponseDto

class AnswerViewModel : ViewModel() {
    var answer = MutableLiveData<String>()
}