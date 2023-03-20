package com.ashishvz.dogimage.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ashishvz.dogimage.data.repository.DogRepository
import com.ashishvz.dogimage.ui.uistate.GenerateImageUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class GenerateImageViewModel(
    private val dogRepository: DogRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<GenerateImageUiState> = MutableStateFlow(
        GenerateImageUiState()
    )
    val uiState: Flow<GenerateImageUiState> get() = _uiState.asStateFlow()

    private fun getImageDogData() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update {
                it.copy(
                    isLoading = true,
                    imgUrl = null,
                    noImage = false,
                    isButtonEnabled = false
                )
            }
            dogRepository.getRemoteDogData()
                .catch {
                    it.printStackTrace()
                }
                .collect { remoteDogData ->
                    if (remoteDogData?.message != null) {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                imgUrl = remoteDogData.message,
                                noImage = false,
                                isButtonEnabled = true
                            )
                        }
                        catchImageUrl(remoteDogData.message)
                    } else {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                imgUrl = null,
                                noImage = true,
                                isButtonEnabled = true
                            )
                        }
                    }
                }
        }
    }

    private fun catchImageUrl(imgUrl: String) {
        dogRepository.cacheDogImageData(imgUrl)
    }

    fun generateImage() {
        getImageDogData()
    }
}