package com.ubcompany.umbba_android.presentation.mypage.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ubcompany.umbba_android.domain.repository.SettingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DeleteRecordDialogViewModel @Inject constructor(
    private val settingRepository: SettingRepository
) : ViewModel() {

    var deleteResponseStatus = MutableLiveData<Int>()

    fun deleteRecord(albumId: Int) {
        viewModelScope.launch {
            settingRepository.deleteRecord(
                albumId.toLong()
            ).onSuccess { response ->
                deleteResponseStatus.value = response.status
                Timber.d("deleteRecord 성공")
            }.onFailure { error ->
                deleteResponseStatus.value = -1
                Timber.e("deleteRecord 실패")
            }
        }
    }
}