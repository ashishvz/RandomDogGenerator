package com.ashishvz.dogimage.data.remote

import com.ashishvz.dogimage.data.remote.models.RemoteDogData
import kotlinx.coroutines.flow.Flow

interface RemoteDataSource {
    suspend fun getRemoteDogImage(): Flow<RemoteDogData?>
}