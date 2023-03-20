package com.ashishvz.dogimage.data.remote

import com.ashishvz.dogimage.api.DogApi
import kotlinx.coroutines.flow.flow

class FakeRemoteDataSource(
    private val fakeDogApi: DogApi
): RemoteDataSource {
    override suspend fun getRemoteDogImage()= flow {
        val api = fakeDogApi.getRemoteDog()
        if (api.isSuccessful) {
            emit(api.body())
        } else {
            emit(null)
        }
    }
}