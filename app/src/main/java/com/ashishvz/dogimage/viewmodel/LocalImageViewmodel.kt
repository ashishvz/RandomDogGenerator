package com.ashishvz.dogimage.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ashishvz.dogimage.data.repository.DogRepository
import com.ashishvz.dogimage.ui.uistate.GetCachedImageUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class LocalImageViewmodel(
    private val dogRepository: DogRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<GetCachedImageUiState> = MutableStateFlow(
        GetCachedImageUiState()
    )
    val uiState: Flow<GetCachedImageUiState> get() = _uiState.asStateFlow()

    init {
        getCachedImages()
    }

    private fun getCachedImages() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update { it.copy(isLoading = true, noDataText = false) }
            dogRepository.getCachedDogData()
                .catch {
                    it.printStackTrace()
                }.collectLatest { cachedData ->
                    if (cachedData.isEmpty())
                        _uiState.update {
                            it.copy(noDataText = true, isLoading = false, imgList = emptyList())
                        }
                    else
                        _uiState.update {
                            it.copy(noDataText = false, isLoading = false, imgList = cachedData)
                        }
                }
        }
    }

    fun clearCache() {
        dogRepository.clearCache()
        getCachedImages()
    }
}