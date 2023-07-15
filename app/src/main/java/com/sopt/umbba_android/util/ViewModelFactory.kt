package com.sopt.umbba_android.util

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sopt.umbba_android.data.datasource.HomeRemoteDataSource
import com.sopt.umbba_android.data.datasource.QuestionAnswerRemoteDataSource
import com.sopt.umbba_android.data.repository.HomeRepositoryImpl
import com.sopt.umbba_android.data.repository.QuestionAnswerRepositoryImpl
import com.sopt.umbba_android.presentation.home.viewmodel.HomeViewModel
import com.sopt.umbba_android.presentation.qna.viewmodel.AnswerViewModel
import com.sopt.umbba_android.presentation.qna.viewmodel.ConfirmAnswerDialogFragmentViewModel
import com.sopt.umbba_android.presentation.qna.viewmodel.QuestionAnswerViewModel

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

        if (modelClass.isAssignableFrom(ConfirmAnswerDialogFragmentViewModel::class.java)){
            return ConfirmAnswerDialogFragmentViewModel(
                QuestionAnswerRepositoryImpl(
                    QuestionAnswerRemoteDataSource()
                )
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}