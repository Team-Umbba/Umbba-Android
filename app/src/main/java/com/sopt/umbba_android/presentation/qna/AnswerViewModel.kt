package com.sopt.umbba_android.presentation.qna

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AnswerViewModel : ViewModel() {
    private val _answer = MutableLiveData<String>()
    var answer: LiveData<String> = _answer
}