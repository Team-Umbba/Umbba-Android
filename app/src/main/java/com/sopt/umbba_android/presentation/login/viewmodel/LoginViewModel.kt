package com.sopt.umbba_android.presentation.login.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.umbba_android.data.model.ServicePool
import com.sopt.umbba_android.data.model.request.LoginRequestDto
import com.sopt.umbba_android.data.model.response.LoginResponseDto
import com.sopt.umbba_android.data.repository.LoginRepositoryImpl
import kotlinx.coroutines.launch

class LoginViewModel(private val loginRepositoryImpl: LoginRepositoryImpl) : ViewModel() {

    private val _getTokenResult: MutableLiveData<LoginResponseDto.LoginData.TokenData> = MutableLiveData()
    val getTokenResult: LiveData<LoginResponseDto.LoginData.TokenData>
        get() = _getTokenResult

    fun login(fcmToken: String) {
        viewModelScope.launch {
            Log.e("yeonjin", "서버 연결 : fcmToken.$fcmToken")
            loginRepositoryImpl.postLogin(
                LoginRequestDto(
                    "KAKAO",
                    fcmToken
                )
            ).onSuccess { response ->
                Log.e("yeonjin", "login 성공")
                _getTokenResult.value = response.data.tokenDto
            }.onFailure { error ->
                Log.e("yeonjin", "login 실패 " + error.message)
            }
        }
    }
}