package com.sopt.umbba_android.presentation.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.google.firebase.messaging.FirebaseMessaging
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.sopt.umbba_android.R
import com.sopt.umbba_android.data.local.SharedPreferences
import com.sopt.umbba_android.databinding.ActivityLoginBinding
import com.sopt.umbba_android.domain.entity.User
import com.sopt.umbba_android.presentation.MainActivity
import com.sopt.umbba_android.presentation.login.viewmodel.LoginViewModel
import com.sopt.umbba_android.presentation.onboarding.InputInfoActivity
import com.sopt.umbba_android.util.ViewModelFactory
import com.sopt.umbba_android.util.binding.BindingActivity
import com.sopt.umbba_android.util.setOnSingleClickListener
import timber.log.Timber

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
                Timber.e(error, "카카오계정으로 로그인 실패")
            } else if (token != null) {
                Timber.d("카카오계정으로 로그인 성공 " + token.accessToken)
                FirebaseMessaging.getInstance().token.addOnSuccessListener { fcmToken ->
                    SharedPreferences.apply {
                        setKakaoString(USER_KAKAO_TOKEN, token.accessToken)
                    }
                    viewModel.login(fcmToken = fcmToken)
                }
            }
        }

        binding.btnKakaoLogin.setOnSingleClickListener {
            if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
                UserApiClient.instance.loginWithKakaoTalk(this) { token, error ->
                    if (error != null) {
                        Timber.e(error, "카카오톡으로 로그인 실패")

                        if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                            return@loginWithKakaoTalk
                        }

                        UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
                    } else if (token != null) {
                        Timber.d("카카오톡으로 로그인 성공 " + token.accessToken)
                        callback.invoke(token, error)
                    }
                }
            } else {
                UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
            }
        }
    }

    private fun setUserInfo(accessToken: String) {
        SharedPreferences.apply {
            setString(USER_TOKEN, accessToken)
            UserApiClient.instance.me { user, error ->
                if (error == null && user != null) {
                    setString(USER_NICKNAME, user.kakaoAccount?.profile?.nickname)
                    setString(USER_IMAGE, user.kakaoAccount?.profile?.profileImageUrl)
                }
            }
        }
    }

    private fun setAutoLogin() {
        if (!SharedPreferences.getString(USER_TOKEN).isNullOrBlank()) {
            showOnboardForFirst()
        } else {
            viewModel.getTokenResult.observe(this) { response ->
                setUserInfo(response.accessToken)
                if (SharedPreferences.getOnboardingBoolean(DID_USER_CLEAR_ONBOARD)
                    && SharedPreferences.getInviteCodeBoolean(DID_USER_CLEAR_INVITE_CODE)
                ) {
                    goMainActivity()
                } else {
                    goAgreePrivacyUseActivity()
                }
            }
        }
    }

    private fun showOnboardForFirst() {
        if (!SharedPreferences.getOnboardingBoolean(DID_USER_CLEAR_ONBOARD)) {
            if (SharedPreferences.getInviteCodeBoolean(DID_USER_CLEAR_INVITE_CODE)) {
                goInputInfoActivity()
            } else {
                goAgreePrivacyUseActivity()
            }
        } else {
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

    private fun goInputInfoActivity() {
        val userData = User(isReceiver = true)
        startActivity(
            Intent(
                this,
                InputInfoActivity::class.java
            ).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).apply {
                putExtra("userData", userData)
            }
        )
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
        const val DID_USER_CLEAR_ONBOARD = "DID_USER_CLEAR_ONBOARD"
        const val DID_USER_CLEAR_INVITE_CODE = "DID_USER_CLEAR_INVITE_CODE"
    }
}