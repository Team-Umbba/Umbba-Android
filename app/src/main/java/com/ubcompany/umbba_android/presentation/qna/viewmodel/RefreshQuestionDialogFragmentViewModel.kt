package com.ubcompany.umbba_android.presentation.qna.viewmodel

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ubcompany.umbba_android.data.model.request.RefreshQuestionRequestDto
import com.ubcompany.umbba_android.domain.repository.QuestionAnswerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class RefreshQuestionDialogFragmentViewModel @Inject constructor(private val questionAnswerRepository: QuestionAnswerRepository) :
    ViewModel() {

    var responseStatus = MutableLiveData<Int>()
    var questionId = MutableLiveData<Long>()
    var newQuestion = MutableLiveData<String>()

    fun setDataFromBundle(bundle: Bundle?) {
        questionId.value = bundle?.getLong("questionId")
        newQuestion.value = bundle?.getString("newQuestion")
    }

    fun patchRefreshQuestion(refreshQuestionRequestDto: RefreshQuestionRequestDto) {
        viewModelScope.launch {
            questionAnswerRepository.patchRefreshQuestion(refreshQuestionRequestDto)
                .onSuccess { response ->
                    responseStatus.value = response.status
                    Timber.d("patchRefreshQuestion 성공")
                }.onFailure { error ->
                    responseStatus.value = -1
                    Timber.e("postAnswer 실패 $error")
                }
        }
    }
}