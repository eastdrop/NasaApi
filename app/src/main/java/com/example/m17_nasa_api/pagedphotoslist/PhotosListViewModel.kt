package com.example.m17_nasa_api.pagedphotoslist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.m17_nasa_api.models.PhotoResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class PhotosListViewModel : ViewModel() {
    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    val filterEnabled = MutableStateFlow(false)

    val pagedPhotos: Flow<PagingData<PhotoResponse>> = Pager(
        config = PagingConfig(pageSize = 5),
        pagingSourceFactory = { PhotosPagingSource() }
    ).flow.cachedIn(viewModelScope)
}