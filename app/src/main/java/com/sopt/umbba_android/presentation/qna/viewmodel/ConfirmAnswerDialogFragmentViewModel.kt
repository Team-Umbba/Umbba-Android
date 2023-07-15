package com.sopt.umbba_android.presentation.qna.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.umbba_android.data.model.request.AnswerRequestDto
import com.sopt.umbba_android.data.repository.QuestionAnswerRepositoryImpl
import kotlinx.coroutines.launch

class ConfirmAnswerDialogFragmentViewModel(private val questionAnswerRepositoryImpl: QuestionAnswerRepositoryImpl) : ViewModel() {
    fun postAnswer(answerRequestDto: AnswerRequestDto) {
        viewModelScope.launch {
            questionAnswerRepositoryImpl.postAnswer(answerRequestDto)
                .onSuccess {
                    Log.e("hyeon", "postAnswer 성공")
                }.onFailure { error ->
                    Log.e("hyeon", "postAnswer 실패  " + error.message)
                }
        }
    }
}