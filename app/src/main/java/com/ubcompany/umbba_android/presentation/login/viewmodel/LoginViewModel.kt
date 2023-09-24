package com.ubcompany.umbba_android.presentation.login.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ubcompany.umbba_android.data.model.request.LoginRequestDto
import com.ubcompany.umbba_android.data.model.response.LoginResponseDto
import com.ubcompany.umbba_android.data.repository.LoginRepositoryImpl
import com.ubcompany.umbba_android.domain.repository.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginRepository: LoginRepository) : ViewModel() {

    private val _getTokenResult: MutableLiveData<LoginResponseDto.LoginData> = MutableLiveData()
    val getTokenResult: LiveData<LoginResponseDto.LoginData>
        get() = _getTokenResult

    fun login(fcmToken: String) {
        viewModelScope.launch {
            loginRepository.postLogin(
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