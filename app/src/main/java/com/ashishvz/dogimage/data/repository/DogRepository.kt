package com.ashishvz.dogimage.data.repository

import com.ashishvz.dogimage.data.remote.models.RemoteDogData
import com.ashishvz.dogimage.data.local.models.CachedDogData
import kotlinx.coroutines.flow.Flow

interface DogRepository {
    suspend fun getRemoteDogData(): Flow<RemoteDogData?>
    fun getCachedDogData(): Flow<MutableList<CachedDogData>>
    fun cacheDogImageData(imgUrl: String)
    fun clearCache()
}