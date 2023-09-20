package com.ubcompany.umbba_android.presentation.qna.viewmodel

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ubcompany.umbba_android.data.model.request.AnswerRequestDto
import com.ubcompany.umbba_android.data.repository.QuestionAnswerRepositoryImpl
import com.ubcompany.umbba_android.domain.repository.QuestionAnswerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ConfirmAnswerDialogFragmentViewModel @Inject constructor(private val questionAnswerRepository: QuestionAnswerRepository) :
    ViewModel() {

    var responseStatus = MutableLiveData<Int>()
    var answer = MutableLiveData<String>()
    var question = MutableLiveData<String>()
    var section = MutableLiveData<String>()
    var topic = MutableLiveData<String>()

    fun setDataFromBundle(bundle: Bundle?) {
        answer.value = bundle?.getString("answer")
        question.value = bundle?.getString("question")
        section.value = bundle?.getString("section")
        topic.value = bundle?.getString("topic")
        Timber.e("setData ${topic.value}")
    }

    fun postAnswer(answerRequestDto: AnswerRequestDto) {
        viewModelScope.launch {
            questionAnswerRepository.postAnswer(answerRequestDto)
                .onSuccess { reseponse ->
                    responseStatus.value = reseponse.status
                    Timber.d("postAnswer 성공")
                }.onFailure { error ->
                    responseStatus.value = -1
                    Timber.e("postAnswer 실패 $error")
                }
        }
    }
}