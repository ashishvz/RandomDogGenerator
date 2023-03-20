package com.ashishvz.dogimage.data.local

import com.ashishvz.dogimage.data.local.models.CachedDogData
import kotlinx.coroutines.flow.flow
import java.util.Date

class FakeLocalDataSource(
    private val fakeLruCache: HashMap<Long, String> // Using HashMap as LruCache is resource based and cannot be mocked
) : LocalDataSource {

    override fun getCacheDogImageData() = flow {
        val cacheSnapShot: Map<Long, String> = fakeLruCache
        emit(cacheSnapShot.map {
            CachedDogData(it.value)
        }.toMutableList())
    }

    override fun cacheDogImageData(imgUrl: String) {
        fakeLruCache[Date().time] = imgUrl
    }

    override fun clearCache() {
        fakeLruCache.clear()
    }
}