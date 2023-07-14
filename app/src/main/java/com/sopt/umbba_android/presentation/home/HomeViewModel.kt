package com.sopt.umbba_android.presentation.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.umbba_android.data.model.response.HomeResponseDto
import com.sopt.umbba_android.data.repository.HomeRepositoryImpl
import kotlinx.coroutines.launch

class HomeViewModel(private val homeRepositoryImpl: HomeRepositoryImpl) : ViewModel() {

    init{
        getHomeData()
    }

    private var _homeData = MutableLiveData<HomeResponseDto.HomeData>()
    val homeData: LiveData<HomeResponseDto.HomeData> = _homeData

    private fun getHomeData() {
        viewModelScope.launch {
            homeRepositoryImpl.getHomeData()
                .onSuccess { response ->
                    Log.e("hyeon", "getHomeData 성공")
                    _homeData.value = response.data
                }.onFailure { error ->
                    Log.e("hyeon", "getHomeData 실패  " + error.message)
                }
        }
    }
}