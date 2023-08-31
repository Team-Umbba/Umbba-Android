package com.sopt.umbba_android.presentation.qna.viewmodel

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AnswerViewModel : ViewModel() {
    var answer = MutableLiveData<String>()
    var question = MutableLiveData<String>()
    var section = MutableLiveData<String>()
    var topic = MutableLiveData<String>()

    fun setDataFromIntent(intent: Intent) {
        question.value = intent.getStringExtra("question")
        topic.value =
            "#${intent.getIntExtra("index", -1)} ${intent.getStringExtra("topic")}"
        section.value = intent.getStringExtra("section")
    }

    fun setBundleArgument(bundle: Bundle): Bundle {
        bundle.apply {
            putString("question", question.value)
            putString("topic", topic.value)
            putString("section", section.value)
            putString("answer", answer.value)
        }
        return bundle
    }
}