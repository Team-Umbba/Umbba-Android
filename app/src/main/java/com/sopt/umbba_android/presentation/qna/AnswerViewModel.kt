package com.sopt.umbba_android.presentation.qna

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AnswerViewModel : ViewModel() {
    private var _answer = MutableLiveData<String>()
    val answer: LiveData<String> = _answer
}