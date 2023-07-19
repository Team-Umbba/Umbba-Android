package com.sopt.umbba_android.presentation.qna.viewmodel

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