package com.sopt.umbba_android.presentation.onboarding.quest

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class QuestViewModel : ViewModel() {
    val isClickedYes = MutableLiveData<Boolean>()
    val isClickedNo = MutableLiveData<Boolean>()
    val isClickedAmbiguous = MutableLiveData<Boolean>()

    val isClickedComplete = MutableLiveData<Boolean>()

    fun checkButtonComplete() {
        isClickedComplete.value = isClickedYes.value == true || isClickedNo.value == true || isClickedAmbiguous.value == true
    }

}