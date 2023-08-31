package com.ubcompany.umbba_android.data.interceptor

import com.ubcompany.umbba_android.data.local.SharedPreferences
import com.ubcompany.umbba_android.presentation.login.LoginActivity.Companion.USER_KAKAO_TOKEN
import com.ubcompany.umbba_android.presentation.login.LoginActivity.Companion.USER_TOKEN
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val token = if (SharedPreferences.getString(USER_TOKEN).isNullOrBlank()) {
            SharedPreferences.getKakaoString(USER_KAKAO_TOKEN)
        } else {
            "Bearer ${SharedPreferences.getString(USER_TOKEN)}"
        }

        val originalRequest = chain.request()

        val headerRequest = originalRequest.newBuilder()
            .header(
                "Authorization",
                "$token"
            )
            .build()
        return chain.proceed(headerRequest)
    }
}