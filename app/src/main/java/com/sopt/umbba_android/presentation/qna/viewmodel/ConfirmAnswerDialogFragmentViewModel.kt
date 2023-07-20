package com.sopt.umbba_android.presentation.qna.viewmodel

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.umbba_android.data.model.request.AnswerRequestDto
import com.sopt.umbba_android.data.repository.QuestionAnswerRepositoryImpl
import kotlinx.coroutines.launch

class ConfirmAnswerDialogFragmentViewModel(private val questionAnswerRepositoryImpl: QuestionAnswerRepositoryImpl) :
    ViewModel() {

    var responseStatus = MutableLiveData<Int>()
    var answer = MutableLiveData<String>()
    var question = MutableLiveData<String>()
    var section = MutableLiveData<String>()
    var topic = MutableLiveData<String>()

    fun setDataFromBundle(bundle: Bundle) {
        answer.value = bundle.getString("answer")
        question.value = bundle.getString("question")
        section.value = bundle.getString("section")
        topic.value = bundle.getString("topic")
    }

    fun postAnswer(answerRequestDto: AnswerRequestDto) {
        viewModelScope.launch {
            questionAnswerRepositoryImpl.postAnswer(answerRequestDto)
                .onSuccess { reseponse ->
                    Log.e("hyeon", "postAnswer 성공")
                    responseStatus.value = reseponse.status
                }.onFailure { error ->
                    Log.e("hyeon", "postAnswer 실패  " + error.message)
                    responseStatus.value = -1
                }
        }
    }
}