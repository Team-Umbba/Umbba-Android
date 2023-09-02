package com.ubcompany.umbba_android.presentation.qna.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ubcompany.umbba_android.data.model.response.ListQuestionAnswerResponseDto
import com.ubcompany.umbba_android.data.model.response.QuestionAnswerResponseDto
import com.ubcompany.umbba_android.data.repository.QuestionAnswerRepositoryImpl
import kotlinx.coroutines.launch
import timber.log.Timber

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

    private var _topicTitle = MutableLiveData<String>()
    val topicTitle: LiveData<String> = _topicTitle

    fun getQuestionAnswer() {
        viewModelScope.launch {
            questionAnswerRepositoryImpl.getQuestionAnswer()
                .onSuccess { response ->
                    _qnaResponse.value = response.data
                    isMyAnswer.value = response.data.isMyAnswer
                    isOpponentAnswer.value = response.data.isOpponentAnswer
                    _topicTitle.value = "#${response.data.index} ${response.data.topic}"
                    appbarSection.value = response.data.section.toString()
                    Timber.d("getQuestionAnswer 성공")
                }.onFailure { error ->
                    Timber.e("getQuestionAnswer 실패 $error")
                }
        }
    }

    fun getListQuestionAnswer(qnaId: Long) {
        viewModelScope.launch {
            questionAnswerRepositoryImpl.getListQuestionAnswer(qnaId)
                .onSuccess { response ->
                    _listQnaResponse.value = response.data
                    appbarSection.value = response.data.section.toString()
                    _topicTitle.value = "#${response.data.index} ${response.data.topic}"
                    Timber.e("getListQuestionAnswer 성공")
                }.onFailure { error ->
                    Timber.e("getListQuestionAnswer 실패 $error")
                }
        }
    }
}