package com.sopt.umbba_android.presentation.qna.viewmodel

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sopt.umbba_android.data.entity.AnswerIntentResponse

class AnswerViewModel : ViewModel() {
    var answer = MutableLiveData<String>()
    var question = MutableLiveData<String>()
    var section = MutableLiveData<String>()
    var topic = MutableLiveData<String>()

    fun setDataFromIntent(intent: Intent) {
        Log.e("hyeon", "question 들어왔니?" + intent.getStringExtra("question"))
        question.value = intent.getStringExtra("question")
        topic.value =
            "#${intent.getIntExtra("index", -1)} ${intent.getStringExtra("topic")}"
        Log.e("hyeon", "index의 값은?" + intent.getIntExtra("index", -1))
        section.value = intent.getStringExtra("section")
    }

    fun setBundleArgument(bundle:Bundle) : Bundle{
        bundle.apply {
            putString("question",question.value)
            putString("topic", topic.value)
            putString("section", section.value)
            putString("answer", answer.value)
        }
        return bundle
    }
}