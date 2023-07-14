package com.sopt.umbba_android.data.model

import com.google.firebase.BuildConfig
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.sopt.umbba_android.data.service.HomeService
import com.sopt.umbba_android.data.service.ListService
import com.sopt.umbba_android.data.service.OnBoardingService
import com.sopt.umbba_android.data.service.QuestionAnswerService
import com.sopt.umbba_android.data.service.SettingService
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

object ApiFactory {
    private val client by lazy {
        OkHttpClient.Builder().addInterceptor(
            AuthInterceptor()
        ).build()
    }
    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("d")
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
    val onBoardingService = ApiFactory.create<OnBoardingService>()
    val listService = ApiFactory.create<ListService>()
}