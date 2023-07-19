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

class HomeViewModel(private val homeRepositoryImpl: HomeRepositoryImpl) : ViewModel() {
    init {
        getHomeData()
        getResponseCase()
    }

    private var _responseCaseData = MutableLiveData<HomeCaseResponseDto.HomeCaseData>()
    val responseCaseData: LiveData<HomeCaseResponseDto.HomeCaseData> = _responseCaseData
    private var _homeData = MutableLiveData<HomeResponseDto.HomeData>()
    val homeData: LiveData<HomeResponseDto.HomeData> = _homeData

    private var _sectionTitle = MutableLiveData<String>()
    val sectionTitle:LiveData<String> = _sectionTitle

    private fun getHomeData() {
        viewModelScope.launch {
            homeRepositoryImpl.getHomeData()
                .onSuccess { response ->
                    Log.e("hyeon", "getHomeData 성공")
                    _homeData.value = response.data
                    _sectionTitle.value ="#${response.data.index} ${response.data.section}"
                }.onFailure { error ->
                    Log.e("hyeon", "getHomeData 실패  " + error.message)
                }
        }
    }

    private fun getResponseCase() {
        viewModelScope.launch {
            homeRepositoryImpl.getResponseCase()
                .onSuccess { response ->
                    _responseCaseData.value = response.data
                    Log.e("hyeon", "getResponseCode 성공")
                }.onFailure { error ->
                    Log.e("hyeon", "getResponseCode 실패  " + error.message)
                }
        }
    }
}