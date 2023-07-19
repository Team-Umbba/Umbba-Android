package com.sopt.umbba_android.presentation.qna.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.umbba_android.data.model.response.ListQuestionAnswerResponseDto
import com.sopt.umbba_android.data.model.response.QuestionAnswerResponseDto
import com.sopt.umbba_android.data.repository.QuestionAnswerRepositoryImpl
import kotlinx.coroutines.launch

class QuestionAnswerViewModel(private val questionAnswerRepositoryImpl: QuestionAnswerRepositoryImpl) :
    ViewModel() {
    private var _qnaResponse = MutableLiveData<QuestionAnswerResponseDto.QnaData>()
    val qnaResponse: LiveData<QuestionAnswerResponseDto.QnaData> = _qnaResponse

    private var _listQnaResponse = MutableLiveData<ListQuestionAnswerResponseDto.QnaData>()
    val listQnaResponse: LiveData<ListQuestionAnswerResponseDto.QnaData> = _listQnaResponse

    var isMyAnswer = MutableLiveData<Boolean?>()
    var isOpponentAnswer = MutableLiveData<Boolean?>()

    var appbarSection = MutableLiveData<String>()

    var isBeforeList = MutableLiveData<Boolean?>()

    private var _sectionTitle = MutableLiveData<String>()
    val sectionTitle: LiveData<String> = _sectionTitle

    fun getQuestionAnswer() {
        viewModelScope.launch {
            questionAnswerRepositoryImpl.getQuestionAnswer()
                .onSuccess { response ->
                    Log.e("hyeon", "getQuestionAnswer 성공")
                    _qnaResponse.value = response.data
                    isMyAnswer.value = response.data.isMyAnswer
                    isOpponentAnswer.value = response.data.isOpponentAnswer
                    _sectionTitle.value = "#${response.data.index} ${response.data.section}"
                    appbarSection.value = response.data.section.toString()
                }.onFailure { error ->
                    Log.e("hyeon", "getQuestionAnswer 실패  " + error.message)
                }
        }
    }

    fun getListQuestionAnswer(qnaId: Long) {
        viewModelScope.launch {
            questionAnswerRepositoryImpl.getListQuestionAnswer(qnaId)
                .onSuccess { response ->
                    _listQnaResponse.value = response.data
                    appbarSection.value = response.data.section.toString()
                    _sectionTitle.value = "#${response.data.index} ${response.data.section}"
                    Log.e("hyeon", "getListQuestionAnswer 성공")
                }.onFailure { error ->
                    Log.e("hyeon", "getListQuestionAnswer 실패" + error.message)
                }
        }
    }
}