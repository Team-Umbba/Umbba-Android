package com.sopt.umbba_android.data.model

import com.google.firebase.BuildConfig
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.sopt.umbba_android.data.service.QuestionAnswerService
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

object ApiFactory {
    private val client by lazy{
        OkHttpClient.Builder().addInterceptor(
            HttpLoggingInterceptor().apply{
                level =
                    if(BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
            }
        ).build()
    }
    val retrofit: Retrofit by lazy{
        Retrofit.Builder()
            .baseUrl("d")
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .client(client)
            .build()
    }

    inline fun <reified T> create():T = retrofit.create(T::class.java)
}

object ServicePool{
    val questionAnswerService = ApiFactory.create<QuestionAnswerService>()
}