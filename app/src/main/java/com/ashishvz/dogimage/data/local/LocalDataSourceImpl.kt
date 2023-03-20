package com.ashishvz.dogimage.data.local

import android.app.Application
import android.graphics.Bitmap
import android.util.LruCache
import com.ashishvz.dogimage.data.local.models.CachedDogData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import java.io.File
import java.util.Date

class LocalDataSourceImpl(
    private val cache: LruCache<Long, String>
) : LocalDataSource {

    override fun getCacheDogImageData() = flow {
        val cacheSnapShot: Map<Long, String> = cache.snapshot()
        emit(cacheSnapShot.map {
            CachedDogData(it.value)
        }.toMutableList())
    }

    override fun cacheDogImageData(imgUrl: String) {
        cache.put(Date().time, imgUrl)
    }

    override fun clearCache() {
        cache.evictAll()
    }
}