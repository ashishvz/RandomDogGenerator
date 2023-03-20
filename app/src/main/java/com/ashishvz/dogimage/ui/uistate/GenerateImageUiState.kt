package com.ashishvz.dogimage.ui.uistate

data class GenerateImageUiState(
    val imgUrl: String? = null,
    val error: String? = null,
    val isLoading: Boolean = false,
    val noImage: Boolean = true,
    val isButtonEnabled: Boolean = true
)
