package com.ashishvz.dogimage.data.repository

import com.ashishvz.dogimage.data.remote.RemoteDataSource
import com.ashishvz.dogimage.data.remote.models.RemoteDogData
import com.ashishvz.dogimage.data.local.models.CachedDogData
import com.ashishvz.dogimage.data.local.LocalDataSource
import kotlinx.coroutines.flow.Flow

class DogRepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : DogRepository {
    override suspend fun getRemoteDogData() = remoteDataSource.getRemoteDogImage()

    override fun getCachedDogData() = localDataSource.getCacheDogImageData()

    override fun cacheDogImageData(imgUrl: String) {
        return localDataSource.cacheDogImageData(imgUrl)
    }

    override fun clearCache() = localDataSource.clearCache()
}