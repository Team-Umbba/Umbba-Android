package com.ubcompany.umbba_android.presentation.qna.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ubcompany.umbba_android.data.model.response.ListQuestionAnswerResponseDto
import com.ubcompany.umbba_android.data.model.response.QuestionAnswerResponseDto
import com.ubcompany.umbba_android.data.repository.QuestionAnswerRepositoryImpl
import com.ubcompany.umbba_android.domain.repository.QuestionAnswerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class QuestionAnswerViewModel @Inject constructor(private val questionAnswerRepository: QuestionAnswerRepository) :
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
            questionAnswerRepository.getQuestionAnswer()
                .onSuccess { response ->
                    _qnaResponse.value = response.data
                    isMyAnswer.value = response.data.isMyAnswer
                    isOpponentAnswer.value = response.data.isOpponentAnswer
                    _topicTitle.value = "#${response.data.index} ${response.data.topic}"
                    appbarSection.value = response.data.section.toString()
                    Timber.d("getQuestionAnswer 성공")
                }.onFailure { error ->
                    if(error is HttpException){
                        val errorBody = error.response()?.errorBody()?.string()
                        Log.e("hyeon","겟 실패 ${errorBody}")
                    }
                    Timber.e("getQuestionAnswer 실패 $error")
                    Log.e("hyeon", "getQuestionAnswer 실패 ${error.message} ")
                }
        }
    }

    fun getListQuestionAnswer(qnaId: Long) {
        viewModelScope.launch {
            questionAnswerRepository.getListQuestionAnswer(qnaId)
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