package com.ashishvz.dogimage.di

import android.graphics.Bitmap
import android.util.LruCache
import com.ashishvz.dogimage.data.local.LocalDataSource
import com.ashishvz.dogimage.data.local.LocalDataSourceImpl
import com.ashishvz.dogimage.utility.Constants
import com.bumptech.glide.disklrucache.DiskLruCache
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import java.io.File
import java.io.FileInputStream
import java.io.ObjectInputStream

val localDataSourceModule = module {
    single<LocalDataSource> {
        LocalDataSourceImpl(get())
    }

    single {
        val cache = LruCache<Long, String>(Constants.CACHE_SIZE)
        val file = File(androidApplication().cacheDir, Constants.CACHE_FILE_NAME)
        if (file.exists()) {
            val inputStream = ObjectInputStream(FileInputStream(file))
            val snapshot = inputStream.readObject() as LinkedHashMap<*, *>
            cache.evictAll()
            snapshot.map {
                cache.put(it.key as Long?, it.value as String?)
            }
        }
        cache
    }
}