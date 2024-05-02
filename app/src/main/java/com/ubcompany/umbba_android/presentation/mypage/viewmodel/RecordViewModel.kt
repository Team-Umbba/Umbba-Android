package com.ubcompany.umbba_android.presentation.mypage.viewmodel

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ubcompany.umbba_android.data.model.request.RecordImageRequestDto
import com.ubcompany.umbba_android.data.model.response.RecordListResponseDto
import com.ubcompany.umbba_android.domain.repository.SettingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class RecordViewModel @Inject constructor(
    private val settingRepository: SettingRepository,
) : ViewModel() {

    val fileName = MutableLiveData<String>()
    val presignedUrl = MutableLiveData<String>()

    val isImageSaved = MutableLiveData<Boolean>()
    val isUrlSaved = MutableLiveData<Boolean>()

    private var _recordListResponse = MutableLiveData<List<RecordListResponseDto.RecordListData>>()
    val recordListResponse: LiveData<List<RecordListResponseDto.RecordListData>> =
        _recordListResponse

    fun initIsImageSaved() {
        isImageSaved.value = false
    }

    fun initIsUrlSaved() {
        isUrlSaved.value = false
    }

    fun receivePresignedUrl() {
        viewModelScope.launch {
            settingRepository.getImageUrl(
                RecordImageRequestDto(
                    imgPrefix = PREFIX
                )
            ).onSuccess {
                fileName.value = it.data.fileName
                presignedUrl.value = it.data.url
                isUrlSaved.value = true
                Timber.d("receivePresignedUrl 성공")
            }.onFailure {
                Timber.e("receivePresignedUrl 실패")
            }
        }
    }

    fun uploadImage(url: String, imageBitmap: Bitmap) {
        viewModelScope.launch {
            settingRepository.uploadImage(
                url, imageBitmap
            )
        }
        Timber.d("uploadImage 성공")
        isImageSaved.value = true
    }

    fun getRecordListData() {
        viewModelScope.launch {
            settingRepository.getRecordList()
                .onSuccess {
                    _recordListResponse.value = it.data
                    Timber.d("getRecordListData 성공")
                }.onFailure {
                    Timber.e("getRecordListData 실패")
                }
        }
    }

    companion object {
        const val PREFIX = "album/"
    }
}