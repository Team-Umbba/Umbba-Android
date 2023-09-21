package com.ubcompany.umbba_android.presentation.onboarding.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class InputInfoViewModel @Inject constructor() : ViewModel() {
    val name = MutableLiveData<String>()
    val man = MutableLiveData<Boolean>()
    val woman = MutableLiveData<Boolean>()
    val year = MutableLiveData<String>()

    val isAllInfoComplete = MutableLiveData<Boolean>()

    fun checkInfoComplete() {
        return if (!name.value.isNullOrEmpty() && !year.value.isNullOrEmpty() && ((man.value == true) || (woman.value == true))) {
            isAllInfoComplete.value = true
        } else {
            isAllInfoComplete.value = false
        }
    }
}