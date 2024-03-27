package com.ubcompany.umbba_android.presentation.mypage.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UploadRecordViewModel @Inject constructor() : ViewModel() {
    val title = MutableLiveData<String>()
    val description = MutableLiveData<String>()

    val isAllInfoComplete = MutableLiveData<Boolean>()

    fun checkInfoComplete() {
        return if (!title.value.isNullOrEmpty() && !description.value.isNullOrEmpty()) {
            isAllInfoComplete.value = true
        } else {
            isAllInfoComplete.value = false
        }
    }

}