package com.ashishvz.dogimage.ui.uistate

import com.ashishvz.dogimage.data.local.models.CachedDogData

data class GetCachedImageUiState(
    val imgList: List<CachedDogData> = emptyList(),
    val noDataText: Boolean = false,
    val isLoading: Boolean = true
)