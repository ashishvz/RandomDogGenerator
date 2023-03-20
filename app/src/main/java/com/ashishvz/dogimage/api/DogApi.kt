package com.ashishvz.dogimage.api

import com.ashishvz.dogimage.data.remote.models.RemoteDogData
import com.ashishvz.dogimage.utility.Constants
import retrofit2.Response
import retrofit2.http.GET

interface DogApi {
    @GET(Constants.GET_RANDOM_IMAGE)
    suspend fun getRemoteDog(): Response<RemoteDogData>
}