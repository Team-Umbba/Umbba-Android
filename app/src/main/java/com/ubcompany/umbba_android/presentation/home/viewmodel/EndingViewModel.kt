package com.ubcompany.umbba_android.presentation.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ubcompany.umbba_android.data.model.response.BaseResponseDto
import com.ubcompany.umbba_android.data.model.response.HomeCaseResponseDto
import com.ubcompany.umbba_android.domain.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class EndingViewModel @Inject constructor(private val homeRepository: HomeRepository) :
    ViewModel() {

    private var _baseResponse = MutableLiveData<BaseResponseDto>()
    val baseResponse: LiveData<BaseResponseDto> = _baseResponse

    var errorCode = MutableLiveData<Int>()

    fun patch7DaysAfter() {
        viewModelScope.launch {
            homeRepository.patch7DaysAfter()
                .onSuccess { response ->
                    _baseResponse.value = response
                    Timber.d("patch7DaysAfter 성공")
                }.onFailure { error ->
                    if (error is HttpException) {
                        errorCode.value = error.code()
                    }
                    Timber.e("patch7DaysAfter 실패")
                }
        }
    }
}