package com.ubcompany.umbba_android.presentation.mypage.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ubcompany.umbba_android.data.model.request.RecordUploadRequestDto
import com.ubcompany.umbba_android.domain.repository.SettingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UploadRecordViewModel @Inject constructor(
    private val settingRepository: SettingRepository
) : ViewModel() {
    val title = MutableLiveData<String>()
    val description = MutableLiveData<String>()
    val imgName = MutableLiveData<String>()

    val isAllInfoComplete = MutableLiveData<Boolean>()

    fun checkInfoComplete() {
        return if (!title.value.isNullOrEmpty() && !description.value.isNullOrEmpty()) {
            isAllInfoComplete.value = true
        } else {
            isAllInfoComplete.value = false
        }
    }

    fun uploadRecord() {
        viewModelScope.launch {
            settingRepository.uploadRecord(
                RecordUploadRequestDto(
                    title = title.value.toString(),
                    content = description.value.toString(),
                    imgName = imgName.value.toString()
                )
            ).onSuccess {
                Log.d("yeonjin", "uploadRecord 성공")
            }.onFailure { error ->
                Log.d("yeonjin", "uploadRecord 실패 $error")
            }
        }
    }

}