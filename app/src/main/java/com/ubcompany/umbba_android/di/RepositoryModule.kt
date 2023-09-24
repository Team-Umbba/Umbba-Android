package com.ubcompany.umbba_android.di

import com.ubcompany.umbba_android.data.repository.HomeRepositoryImpl
import com.ubcompany.umbba_android.data.repository.ListRepositoryImpl
import com.ubcompany.umbba_android.data.repository.LoginRepositoryImpl
import com.ubcompany.umbba_android.data.repository.OnboardingRepositoryImpl
import com.ubcompany.umbba_android.data.repository.QuestionAnswerRepositoryImpl
import com.ubcompany.umbba_android.data.repository.SettingRepositoryImpl
import com.ubcompany.umbba_android.domain.repository.HomeRepository
import com.ubcompany.umbba_android.domain.repository.ListRepository
import com.ubcompany.umbba_android.domain.repository.LoginRepository
import com.ubcompany.umbba_android.domain.repository.OnboardingRepository
import com.ubcompany.umbba_android.domain.repository.QuestionAnswerRepository
import com.ubcompany.umbba_android.domain.repository.SettingRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindHomeRepository(homeRepositoryImpl: HomeRepositoryImpl): HomeRepository

    @Binds
    @Singleton
    abstract fun bindListRepository(listRepositoryImpl: ListRepositoryImpl): ListRepository

    @Binds
    @Singleton
    abstract fun bindLoginRepository(loginRepositoryImpl: LoginRepositoryImpl): LoginRepository

    @Binds
    @Singleton
    abstract fun bindOnboardingRepository(onboardingRepositoryImpl: OnboardingRepositoryImpl): OnboardingRepository

    @Binds
    @Singleton
    abstract fun bindQuestionAnswerRepository(questionAnswerRepositoryImpl: QuestionAnswerRepositoryImpl): QuestionAnswerRepository

    @Binds
    @Singleton
    abstract fun bindSettingRepository(settingRepositoryImpl: SettingRepositoryImpl): SettingRepository
}