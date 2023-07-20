package com.sopt.umbba_android.presentation.list.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.umbba_android.data.model.response.ListResponseDto
import com.sopt.umbba_android.data.repository.ListRepositoryImpl
import kotlinx.coroutines.launch
import timber.log.Timber

class ListViewModel(private val listRepositoryImpl: ListRepositoryImpl) : ViewModel() {

    init{
        getListData(1)
    }

    private var _listResponse = MutableLiveData<List<ListResponseDto.ListData>>()
    val listResponseDto: LiveData<List<ListResponseDto.ListData>> = _listResponse

    fun getListData(sectionId:Int) {
        viewModelScope.launch {
            listRepositoryImpl.getListData(sectionId).onSuccess { response ->
                _listResponse.value = response.data
            }.onFailure { error ->
                Timber.e("getList 실패  " + error.message)
            }
        }
    }
}