package com.ubcompany.umbba_android.presentation.mypage.viewmodel

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ubcompany.umbba_android.data.model.request.RecordImageRequestDto
import com.ubcompany.umbba_android.data.model.response.RecordListResponseDto
import com.ubcompany.umbba_android.domain.repository.SettingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecordViewModel @Inject constructor(
    private val settingRepository: SettingRepository,
) :
    ViewModel() {

    val fileName = MutableLiveData<String>()
    val presignedUrl = MutableLiveData<String>()

    val image = MutableLiveData<Bitmap>()

    private var _recordListResponse = MutableLiveData<List<RecordListResponseDto.RecordListData>>()
    val recordListResponse: LiveData<List<RecordListResponseDto.RecordListData>> = _recordListResponse

    val isImageDelete = MutableLiveData<Boolean>()

    fun receivePresignedUrl() {
        viewModelScope.launch {
            settingRepository.getImageUrl(
                RecordImageRequestDto(
                    imgPrefix = "album/"
                )
            ).onSuccess {
                fileName.value = it.data.fileName
                presignedUrl.value = it.data.url
                Log.d("yeonjin", "receivePresignedUrl 성공 presigned url : ${presignedUrl.value}")
            }.onFailure { error ->
                Log.e("yeonjin", "receivePresignedUrl 실패 $error")
            }
        }
    }
    
    fun uploadImage(url: String, imageBitmap: Bitmap) {
        viewModelScope.launch {
            settingRepository.uploadImage(
                url, imageBitmap
            )
            image.value = imageBitmap
        }
    }

    fun getRecordListData() {
        viewModelScope.launch {
            settingRepository.getRecordList()
                .onSuccess {
                    _recordListResponse.value = it.data
                    Log.d("yeonjin", "record getList 성공")
                }.onFailure { error ->
                    Log.e("yeonjin", "record getList 실패 $error")
                }
        }
    }

    fun deleteRecord(albumId: Int) {
        viewModelScope.launch {
            settingRepository.deleteRecord(
                albumId.toLong()
            ).onSuccess {
                Log.d("yeonjin", "delete record 성공")
            }.onFailure { error ->
                Log.e("yeonjin", "delete record 실패 $error")
            }
        }
    }
}