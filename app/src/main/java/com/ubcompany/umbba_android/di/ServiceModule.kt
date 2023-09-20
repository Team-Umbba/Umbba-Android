package com.ubcompany.umbba_android.di

import com.ubcompany.umbba_android.data.service.HomeService
import com.ubcompany.umbba_android.data.service.ListService
import com.ubcompany.umbba_android.data.service.LoginService
import com.ubcompany.umbba_android.data.service.OnboardingService
import com.ubcompany.umbba_android.data.service.QuestionAnswerService
import com.ubcompany.umbba_android.data.service.SettingService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {
    @Singleton
    @Provides
    fun provideHomeService(retrofit: Retrofit): HomeService =
        retrofit.create(HomeService::class.java)

    @Singleton
    @Provides
    fun provideListService(retrofit: Retrofit): ListService =
        retrofit.create(ListService::class.java)

    @Singleton
    @Provides
    fun provideLoginService(retrofit: Retrofit): LoginService =
        retrofit.create(LoginService::class.java)

    @Singleton
    @Provides
    fun provideOnboardingService(retrofit: Retrofit): OnboardingService =
        retrofit.create(OnboardingService::class.java)

    @Singleton
    @Provides
    fun provideQuestionAnswerService(retrofit: Retrofit): QuestionAnswerService =
        retrofit.create(QuestionAnswerService::class.java)

    @Singleton
    @Provides
    fun provideSettingService(retrofit: Retrofit): SettingService =
        retrofit.create(SettingService::class.java)

}