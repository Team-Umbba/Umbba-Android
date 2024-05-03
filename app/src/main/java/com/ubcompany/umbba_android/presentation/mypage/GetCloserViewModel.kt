package com.ubcompany.umbba_android.presentation.mypage

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ubcompany.umbba_android.data.model.request.CloserAnswerRequestDto
import com.ubcompany.umbba_android.data.model.response.CloserAnswerResponseDto
import com.ubcompany.umbba_android.data.model.response.CloserNextResponseDto
import com.ubcompany.umbba_android.data.model.response.CloserQuestionResponseDto
import com.ubcompany.umbba_android.domain.repository.CloserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class GetCloserViewModel @Inject constructor(private val closerRepository: CloserRepository) :
    ViewModel() {

    var closerActivityCallback: CloserQuestionCallback? = null
    var responseStatus = MutableLiveData<Int>()

    init {
        getCloserQuestion()
    }

    val isCheckedAnswerOne = MutableLiveData<Boolean>()
    val isCheckedAnswerTwo = MutableLiveData<Boolean>()
    val isCompletedAnswer = MutableLiveData<Boolean>()
    val checkedUserAnswer = MutableLiveData<Int>()
    val changeResultFragment = MutableLiveData(false)
    val changeQuestionFragment = MutableLiveData(false)
    var isAnswered = MutableLiveData<Int>()
    private var isCompletedInitFragment = false

    private var _closerQuestionResponse = MutableLiveData<CloserQuestionResponseDto.CloserData>()
    val closerQuestionResponse: LiveData<CloserQuestionResponseDto.CloserData> =
        _closerQuestionResponse

    private var _closerAnswerQuestionResponse = MutableLiveData<CloserAnswerResponseDto>()
    val closerAnswerQuestionResponse: LiveData<CloserAnswerResponseDto> =
        _closerAnswerQuestionResponse

    private var _closerNextQuestionResponse = MutableLiveData<CloserNextResponseDto>()
    val closerNextQuestionResponse: LiveData<CloserNextResponseDto> = _closerNextQuestionResponse

    fun checkCompletedAnswer() {
        isCompletedAnswer.value =
            (isCheckedAnswerOne.value == true) || (isCheckedAnswerTwo.value == true)
    }

    fun showResultFragment() {
        changeResultFragment.value = true
        changeQuestionFragment.value = false
    }

    fun showQuestionFragment() {
        changeQuestionFragment.value = true
        changeResultFragment.value = false
    }

    fun patchUserAnswer() {
        viewModelScope.launch {
            closerRepository.answerCloserQuestion(CloserAnswerRequestDto(checkedUserAnswer.value!!))
                .onSuccess {
                    getCloserQuestion()
                }.onFailure { error ->
                    if (error is HttpException) {
                        val errorBody = error.response()?.errorBody()?.string()
                        Log.e("hyeon", "answer question http 연결 실패 $errorBody")
                    }
                    Log.e("hyeon", "answer question 실패 ${error.message}")
                }
        }
    }

    fun patchNextQuestion() {
        viewModelScope.launch {
            closerRepository.getNextCloserQuestion().onSuccess {
                getCloserQuestion()
            }.onFailure { error ->
                if (error is HttpException) {
                    val errorBody = error.response()?.errorBody()?.string()
                    Log.e("hyeon", "next question http 연결 실패 $errorBody")
                }
                Log.e("hyeon", "next question 실패 ${error.message}")
            }
        }
    }

    private fun getCloserQuestion() {
        viewModelScope.launch {
            closerRepository.getCloserQuestion().onSuccess { response ->
                _closerQuestionResponse.value = response.data
                isAnswered.value = response.data.responseCase
                responseStatus.value = response.status

                if (!isCompletedInitFragment) {
                    closerActivityCallback?.onQuestionRetrieved()
                    isCompletedInitFragment = true
                } // 가까워지기 activity 실행시 한번만 실행되도록 flag 설정.
            }.onFailure { error ->
                if (error is HttpException) {
                    val errorBody = error.response()?.errorBody()?.string()
                    Log.e("hyeon", "getcloser Question http 연결 실패 $errorBody")
                }
                Log.e("hyeon", "closer question 실패 ${error.message}")
            }
        }
    }
}