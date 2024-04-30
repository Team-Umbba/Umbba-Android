package com.ubcompany.umbba_android.presentation.mypage.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ubcompany.umbba_android.data.model.response.MypageResponseDto
import com.ubcompany.umbba_android.domain.repository.SettingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MypageViewModel @Inject constructor(private val settingRepository: SettingRepository) :
    ViewModel() {

    private var _mypageResponse = MutableLiveData<MypageResponseDto.MypageData>()
    val mypageResponse: LiveData<MypageResponseDto.MypageData> = _mypageResponse

    val myUserName = MutableLiveData<String>()
    val myUserType = MutableLiveData<String>()
    val opponentUsername = MutableLiveData<String?>()
    val opponentUserType = MutableLiveData<String>()
    val section = MutableLiveData<String>()
    val matchedDate = MutableLiveData<Long>()
    val qnaCount = MutableLiveData<Int>()
    val isOpponentNull = MutableLiveData<Boolean>()
    val isOpponentExit = MutableLiveData<Boolean>()

    fun getMypage() {
        viewModelScope.launch {
            settingRepository.getMypage()
                .onSuccess { response ->
                    _mypageResponse.value = response.data
                    myUserName.value = response.data.myUserName
                    myUserType.value = response.data.myUserType
                    opponentUsername.value = response.data.opponentUsername
                    opponentUserType.value = response.data.opponentUserType
                    section.value = response.data.section
                    matchedDate.value = response.data.matchedDate
                    qnaCount.value = response.data.qnaCount
                    isOpponentExit.value = response.data.isOpponentExit
                    Timber.d("getMypage 성공")
                }.onFailure { error ->
                    if (error is HttpException) {
                        val errorBody = error.response()?.errorBody()?.string()
                        Log.e("yeonjin", "getMypage http 연결 실패 $errorBody")
                    }
                    Timber.e("getMypage 실패 $error")
                    Log.e("yeonjin", "getMypage 실패 ${error.message}")
                }
        }
    }
}