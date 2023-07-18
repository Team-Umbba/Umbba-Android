package com.sopt.umbba_android.data.interceptor

import android.util.Log
import com.sopt.umbba_android.data.local.SharedPreferences
import com.sopt.umbba_android.presentation.login.LoginActivity.Companion.USER_KAKAO_TOKEN
import com.sopt.umbba_android.presentation.login.LoginActivity.Companion.USER_TOKEN
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val token = if (SharedPreferences.getString(USER_TOKEN).isNullOrBlank()) {
            SharedPreferences.getKakaoString(USER_KAKAO_TOKEN)
            // 저장된 서버 액세스 토큰이 없다면 카카오 토큰 전송 -> 회원가입
        } else {
            "Bearer ${SharedPreferences.getString(USER_TOKEN)}"
            // 저장된 서버 액세스 토큰이 있다면 Bearer 붙여서 전송 -> 로그인
        }

        val originalRequest = chain.request()

        val headerRequest = originalRequest.newBuilder() // 헤더를 추가한 req
            .header(
                "Authorization",
                "$token"
            )
            .build()
        Log.e("yeonjin", "헤더 $token")
        return chain.proceed(headerRequest)
    }
}