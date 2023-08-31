package com.sopt.umbba_android.util

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sopt.umbba_android.data.datasource.HomeRemoteDataSource
import com.sopt.umbba_android.data.datasource.ListRemoteDataSource
import com.sopt.umbba_android.data.datasource.LoginRemoteDataSource
import com.sopt.umbba_android.data.datasource.OnboardingRemoteDataSource
import com.sopt.umbba_android.data.datasource.QuestionAnswerRemoteDataSource
import com.sopt.umbba_android.data.datasource.SettingRemoteDataSource
import com.sopt.umbba_android.data.repository.HomeRepositoryImpl
import com.sopt.umbba_android.data.repository.ListRepositoryImpl
import com.sopt.umbba_android.data.repository.LoginRepositoryImpl
import com.sopt.umbba_android.data.repository.OnboardingRepositoryImpl
import com.sopt.umbba_android.data.repository.QuestionAnswerRepositoryImpl
import com.sopt.umbba_android.data.repository.SettingRepositoryImpl
import com.sopt.umbba_android.presentation.home.viewmodel.HomeViewModel
import com.sopt.umbba_android.presentation.invite.viewmodel.InviteCodeViewModel
import com.sopt.umbba_android.presentation.list.viewmodel.ListViewModel
import com.sopt.umbba_android.presentation.login.viewmodel.LoginViewModel
import com.sopt.umbba_android.presentation.onboarding.quest.QuestViewModel
import com.sopt.umbba_android.presentation.onboarding.viewmodel.SetTimeViewModel
import com.sopt.umbba_android.presentation.qna.viewmodel.AnswerViewModel
import com.sopt.umbba_android.presentation.qna.viewmodel.ConfirmAnswerDialogFragmentViewModel
import com.sopt.umbba_android.presentation.qna.viewmodel.QuestionAnswerViewModel
import com.sopt.umbba_android.presentation.setting.viewmodel.DeleteAccountViewModel
import com.sopt.umbba_android.presentation.setting.viewmodel.ManageAccountViewModel

class ViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(QuestionAnswerViewModel::class.java)) {
            return QuestionAnswerViewModel(
                QuestionAnswerRepositoryImpl(
                    QuestionAnswerRemoteDataSource()
                )
            ) as T
        }
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(
                HomeRepositoryImpl(
                    HomeRemoteDataSource()
                )
            ) as T
        }
        if (modelClass.isAssignableFrom(AnswerViewModel::class.java)) {
            return AnswerViewModel(
            ) as T
        }

        if (modelClass.isAssignableFrom(ConfirmAnswerDialogFragmentViewModel::class.java)) {
            return ConfirmAnswerDialogFragmentViewModel(
                QuestionAnswerRepositoryImpl(
                    QuestionAnswerRemoteDataSource()
                )
            ) as T
        }
        if (modelClass.isAssignableFrom(ListViewModel::class.java)) {
            return ListViewModel(
                ListRepositoryImpl(
                    ListRemoteDataSource()
                )
            ) as T
        }
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(
                LoginRepositoryImpl(
                    LoginRemoteDataSource()
                )
            ) as T
        }

        if (modelClass.isAssignableFrom(InviteCodeViewModel::class.java)) {
            return InviteCodeViewModel(
                OnboardingRepositoryImpl(
                    OnboardingRemoteDataSource()
                )
            ) as T
        }

        if (modelClass.isAssignableFrom(ManageAccountViewModel::class.java)) {
            return ManageAccountViewModel(
                SettingRepositoryImpl(
                    SettingRemoteDataSource()
                )
            ) as T
        }
        if (modelClass.isAssignableFrom(DeleteAccountViewModel::class.java)) {
            return DeleteAccountViewModel(
                SettingRepositoryImpl(
                    SettingRemoteDataSource()
                )
            ) as T
        }
        if (modelClass.isAssignableFrom(SetTimeViewModel::class.java)) {
            return SetTimeViewModel(
                OnboardingRepositoryImpl(
                    OnboardingRemoteDataSource()
                )
            ) as T
        }
        if (modelClass.isAssignableFrom(QuestViewModel::class.java)) {
            return QuestViewModel(
                OnboardingRepositoryImpl(
                    OnboardingRemoteDataSource()
                )
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}