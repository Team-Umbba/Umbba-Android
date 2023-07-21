package com.sopt.umbba_android.presentation.home.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.umbba_android.data.model.response.HomeCaseResponseDto
import com.sopt.umbba_android.data.model.response.HomeResponseDto
import com.sopt.umbba_android.data.repository.HomeRepositoryImpl
import kotlinx.coroutines.launch
import timber.log.Timber

class HomeViewModel(private val homeRepositoryImpl: HomeRepositoryImpl) : ViewModel() {
    init {
        getHomeData()
        getResponseCase()
    }

    private var _responseCaseData = MutableLiveData<HomeCaseResponseDto.HomeCaseData>()
    val responseCaseData: LiveData<HomeCaseResponseDto.HomeCaseData> = _responseCaseData
    private var _homeData = MutableLiveData<HomeResponseDto.HomeData>()
    val homeData: LiveData<HomeResponseDto.HomeData> = _homeData

    private var _topicTitle = MutableLiveData<String>()
    val topicTitle: LiveData<String> = _topicTitle

    private fun getHomeData() {
        viewModelScope.launch {
            homeRepositoryImpl.getHomeData()
                .onSuccess { response ->
                    Timber.d("getHomeData 성공")
                    _homeData.value = response.data
                    _topicTitle.value = "#${response.data.index} ${response.data.topic}"
                }.onFailure { error ->
                    Timber.e("getHomeData 실패 " + error.message)
                }
        }
    }

    fun getResponseCase() {
        viewModelScope.launch {
            homeRepositoryImpl.getResponseCase()
                .onSuccess { response ->
                    _responseCaseData.value = response.data
                    Timber.d("getResponseCode 성공")
                }.onFailure { error ->
                    Timber.e("getResponseCode 실패  " + error.message)
                }
        }
    }
}