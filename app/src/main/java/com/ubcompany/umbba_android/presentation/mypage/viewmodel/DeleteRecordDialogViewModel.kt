package com.ubcompany.umbba_android.presentation.mypage.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ubcompany.umbba_android.domain.repository.SettingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeleteRecordDialogViewModel @Inject constructor(
    private val settingRepository: SettingRepository
): ViewModel() {

    var deleteResponseStatus = MutableLiveData<Int>()

    fun deleteRecord(albumId: Int) {
        viewModelScope.launch {
            settingRepository.deleteRecord(
                albumId.toLong()
            ).onSuccess { response ->
                deleteResponseStatus.value = response.status
                Log.d("yeonjin", "delete record 성공")
            }.onFailure { error ->
                deleteResponseStatus.value = -1
                Log.e("yeonjin", "delete record 실패 $error")
            }
        }
    }
}