package com.sopt.umbba_android.presentation.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.messaging.FirebaseMessaging
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.sopt.umbba_android.R
import com.sopt.umbba_android.data.local.SharedPreferences
import com.sopt.umbba_android.databinding.ActivityLoginBinding
import com.sopt.umbba_android.presentation.MainActivity
import com.sopt.umbba_android.presentation.login.viewmodel.LoginViewModel
import com.sopt.umbba_android.util.ViewModelFactory
import com.sopt.umbba_android.util.binding.BindingActivity

class LoginActivity : BindingActivity<ActivityLoginBinding>(R.layout.activity_login) {

    private val viewModel: LoginViewModel by viewModels {
        ViewModelFactory(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setAutoLogin()
        loginWithKaKao()
    }

    private fun loginWithKaKao() {
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                Log.e("yeonjin", "카카오계정으로 로그인 실패", error)
            } else if (token != null) {
                Log.e("yeonjin", "카카오계정으로 로그인 성공 ${token.accessToken}")
                FirebaseMessaging.getInstance().token.addOnSuccessListener { fcmToken ->
                    Log.e("yeonjin", "FCM 토큰 : $fcmToken")
                    SharedPreferences.apply {
                        setKakaoString(USER_KAKAO_TOKEN, token.accessToken)
                        Log.e("yeonjin", "카카오계정 토큰 저장 : ${getKakaoString(USER_KAKAO_TOKEN)}")
                    }
                    viewModel.login(fcmToken = fcmToken)
                }
            }
        }

        binding.btnKakaoLogin.setOnClickListener {
            if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
                UserApiClient.instance.loginWithKakaoTalk(this) { token, error ->
                    if (error != null) {
                        Log.e("yeonjin", "카카오톡으로 로그인 실패", error)

                        // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
                        // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
                        if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                            return@loginWithKakaoTalk
                        }

                        // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                        UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
                    } else if (token != null) {
                        Log.e("yeonjin", "카카오톡으로 로그인 성공 ${token.accessToken}")
                        callback.invoke(token, error)
                    }
                }
            } else {
                UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
            }
        }
    }

    // 유저 정보, 토큰 세팅
    private fun setUserInfo(accessToken: String) {
        SharedPreferences.apply {
            setString(USER_TOKEN, accessToken)
            Log.e("yeonjin", "서버 토큰 저장 완료 : $accessToken")
            UserApiClient.instance.me { user, error ->
                if (error == null && user != null) {
                    setString(USER_NICKNAME, user.kakaoAccount?.profile?.nickname)
                    setString(USER_IMAGE, user.kakaoAccount?.profile?.profileImageUrl)
                }
            }
        }
    }

    // 자동로그인
    private fun setAutoLogin() {
        //토큰이 저장되어 있다면
        Log.e("yeonjin", "setAutoLogin")
        Log.e("yeonjin", "sharedPreference : ${SharedPreferences.getString(USER_TOKEN)}")
        if (!SharedPreferences.getString(USER_TOKEN).isNullOrBlank()) {
            showOnboardForFirst()
        } else {
            Log.e("yeonjin", "viewmodel gettokenresult")
            viewModel.getTokenResult.observe(this) { response ->
                setUserInfo(response.accessToken)
                goAgreePrivacyUseActivity()
            }
        }
    }

    private fun showOnboardForFirst() {
        //온보딩을 한번이라도 하지 않았다면 - 나중에 온보딩 종료뷰에서 불린 값 true로 바꾸기, 탈퇴할 때 false로 바꾸기
        Log.e("yeonjin", "showOnboardForFirst")
        if (!SharedPreferences.getBoolean(DID_USER_CLEAR_ONBOARD)) {
            goAgreePrivacyUseActivity()
        } else {
            //온보딩을 했다면
            goMainActivity()
        }
    }

    private fun goAgreePrivacyUseActivity() {
        startActivity(
            Intent(
                this,
                AgreePrivacyUseActivity::class.java
            ).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        )
        if (!isFinishing) finish()
    }

    private fun goMainActivity() {
        startActivity(
            Intent(
                this,
                MainActivity::class.java
            ).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        )
        if (!isFinishing) finish()
    }

    companion object {
        const val USER_KAKAO_TOKEN = "USER_KAKAO_TOKEN"
        const val USER_TOKEN = "USER_TOKEN"
        const val USER_NICKNAME = "USER_NICKNAME"
        const val USER_IMAGE = "USER_IMAGE"
        const val DID_USER_CLEAR_ONBOARD = " DID_USER_CLEAR_ONBOARD"
    }
}