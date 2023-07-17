package com.sopt.umbba_android.presentation.qna.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AnswerViewModel : ViewModel() {
    var answer = MutableLiveData<String>()
    var appbarTitle = MutableLiveData<String>()
}