package com.ubcompany.umbba_android.presentation.list.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ubcompany.umbba_android.data.model.response.ListResponseDto
import com.ubcompany.umbba_android.data.repository.ListRepositoryImpl
import com.ubcompany.umbba_android.domain.repository.ListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor (private val listRepository: ListRepository) : ViewModel() {

    init {
        getListData(1)
    }

    private var _listResponse = MutableLiveData<List<ListResponseDto.ListData>>()
    val listResponseDto: LiveData<List<ListResponseDto.ListData>> = _listResponse

    fun getListData(sectionId: Int) {
        viewModelScope.launch {
            listRepository.getListData(sectionId).onSuccess { response ->
                _listResponse.value = response.data
                Timber.d("getList 성공")
            }.onFailure { error ->
                Timber.e("getList 실패 $error")
            }
        }
    }
}