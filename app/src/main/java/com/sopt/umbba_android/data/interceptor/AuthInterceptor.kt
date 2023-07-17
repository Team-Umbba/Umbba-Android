package com.sopt.umbba_android.data.interceptor

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {
    private val token ="d"
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val headerRequest = originalRequest.newBuilder() // 헤더를 추가한 req
            .header(
                "Authorization",
                "Bearer $token"
            )
            .build()

        return chain.proceed(headerRequest)
    }
}