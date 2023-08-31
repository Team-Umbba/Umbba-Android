package com.sopt.umbba_android.presentation.login.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.umbba_android.data.model.request.LoginRequestDto
import com.sopt.umbba_android.data.model.response.LoginResponseDto
import com.sopt.umbba_android.data.repository.LoginRepositoryImpl
import kotlinx.coroutines.launch
import timber.log.Timber

class LoginViewModel(private val loginRepositoryImpl: LoginRepositoryImpl) : ViewModel() {

    private val _getTokenResult: MutableLiveData<LoginResponseDto.LoginData> = MutableLiveData()
    val getTokenResult: LiveData<LoginResponseDto.LoginData>
        get() = _getTokenResult

    fun login(fcmToken: String) {
        viewModelScope.launch {
            loginRepositoryImpl.postLogin(
                LoginRequestDto(
                    "KAKAO",
                    fcmToken
                )
            ).onSuccess { response ->
                Timber.d("login 성공")
                _getTokenResult.value = response.data
            }.onFailure { error ->
                Timber.e("login 실패 $error")
            }
        }
    }
}