package com.ashishvz.dogimage.data.local

import com.ashishvz.dogimage.data.local.models.CachedDogData
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    fun getCacheDogImageData(): Flow<MutableList<CachedDogData>>
    fun cacheDogImageData(imgUrl: String)
    fun clearCache()
}