package com.sopt.umbba_android.presentation.qna

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AnswerViewModel : ViewModel() {
    private val _inputAnswer = MutableLiveData<String>()
    val inputAnswer: MutableLiveData<String>
        get() = _inputAnswer
}