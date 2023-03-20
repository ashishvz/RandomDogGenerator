package com.ashishvz.dogimage.data.remote

import com.ashishvz.dogimage.api.DogApi
import com.ashishvz.dogimage.data.remote.models.RemoteDogData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RemoteDataSourceImpl(
    private val dogApi: DogApi
) : RemoteDataSource {
    override suspend fun getRemoteDogImage() = flow {
        val api = dogApi.getRemoteDog()
        if (api.isSuccessful) {
            emit(api.body())
        } else {
            emit(null)
        }
    }
}