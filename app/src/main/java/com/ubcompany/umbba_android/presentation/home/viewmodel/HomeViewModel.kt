package com.ubcompany.umbba_android.presentation.home.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ubcompany.umbba_android.data.model.response.HomeCaseResponseDto
import com.ubcompany.umbba_android.data.model.response.HomeResponseDto
import com.ubcompany.umbba_android.data.repository.HomeRepositoryImpl
import com.ubcompany.umbba_android.domain.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val homeRepository: HomeRepository) :
    ViewModel() {
    init {
        getHomeData()
    }

    private var _responseCaseData = MutableLiveData<HomeCaseResponseDto.HomeCaseData>()
    val responseCaseData: LiveData<HomeCaseResponseDto.HomeCaseData> = _responseCaseData

    private var _homeData = MutableLiveData<HomeResponseDto.HomeData>()
    val homeData: LiveData<HomeResponseDto.HomeData> = _homeData

    private var _isCloseEnding = MutableLiveData(false)
    val isCloseEnding: LiveData<Boolean> = _isCloseEnding

    private var _isObserveIndex = MutableLiveData(false)
    val isObserveIndex: LiveData<Boolean> = _isObserveIndex

    private var _topicTitle = MutableLiveData<String>()
    val topicTitle: LiveData<String> = _topicTitle

    fun getHomeData() {
        viewModelScope.launch {
            homeRepository.getHomeData()
                .onSuccess { response ->
                    Timber.d("getHomeData 성공")
                    _homeData.value = response.data
                    _topicTitle.value = if (response.data.index != 8) {
                        "#${response.data.index} ${response.data.topic}"
                    } else {
                        "#${(response.data.index) - 1} ${response.data.topic}"
                    }
                }.onFailure { error ->
                    Timber.e("getHomeData 실패 " + error.message)
                }
        }
    }

    fun getResponseCase() {
        viewModelScope.launch {
            homeRepository.getResponseCase()
                .onSuccess { response ->
                    _responseCaseData.value = response.data
                    Timber.d("getResponseCode 성공")
                }.onFailure { error ->
                    Timber.e("getResponseCode 실패  " + error.message)
                }
        }
    }

    fun setStateCloseEnding() {
        _isCloseEnding.value = true
    }

    fun setStateObserveIndex() {
        _isObserveIndex.value = true
    }

    fun isShowedEndingPage(): Boolean {
        return (_isCloseEnding.value == false && _isObserveIndex.value == false)
    }
}