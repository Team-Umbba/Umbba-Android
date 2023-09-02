package com.ubcompany.umbba_android.data.model

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.ubcompany.umbba_android.BuildConfig
import com.ubcompany.umbba_android.data.interceptor.AuthInterceptor
import com.ubcompany.umbba_android.data.service.HomeService
import com.ubcompany.umbba_android.data.service.ListService
import com.ubcompany.umbba_android.data.service.LoginService
import com.ubcompany.umbba_android.data.service.OnboardingService
import com.ubcompany.umbba_android.data.service.QuestionAnswerService
import com.ubcompany.umbba_android.data.service.SettingService
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit

object ApiFactory {

    const val UMBBA_URL = BuildConfig.UMBBA_BASE_URL

    private val client by lazy {
        OkHttpClient.Builder().addInterceptor(
            AuthInterceptor()
        ).build()
    }
    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(UMBBA_URL)
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .client(client)
            .build()
    }

    inline fun <reified T> create(): T = retrofit.create(T::class.java)
}

object ServicePool {
    val questionAnswerService = ApiFactory.create<QuestionAnswerService>()
    val settingService = ApiFactory.create<SettingService>()
    val homeService = ApiFactory.create<HomeService>()
    val onboardingService = ApiFactory.create<OnboardingService>()
    val listService = ApiFactory.create<ListService>()
    val loginService = ApiFactory.create<LoginService>()
}