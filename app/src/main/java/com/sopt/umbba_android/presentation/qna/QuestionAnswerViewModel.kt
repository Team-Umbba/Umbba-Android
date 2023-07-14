package com.sopt.umbba_android.presentation.qna

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.umbba_android.data.model.response.QuestionAnswerResponseDto
import com.sopt.umbba_android.data.repository.QuestionAnswerRepositoryImpl
import kotlinx.coroutines.launch
import timber.log.Timber

class QuestionAnswerViewModel(private val questionAnswerRepositoryImpl: QuestionAnswerRepositoryImpl) :
    ViewModel() {

    private var _qnaResponse = MutableLiveData<QuestionAnswerResponseDto.QnaData>()
    val qnaResponse: LiveData<QuestionAnswerResponseDto.QnaData> = _qnaResponse

    fun getQuestionAnswer() {
        viewModelScope.launch {
            questionAnswerRepositoryImpl.getQuestionAnswer()
                .onSuccess { response ->
                    Log.e("hyeon", "getQuestionAnswer 성공")
                    _qnaResponse.value = response.data
                }.onFailure { error ->
                Log.e("hyeon", "getQuestionAnswer 실패  " + error.message)
            }
        }
    }
}