package com.ashishvz.dogimage

import android.app.Application
import com.ashishvz.dogimage.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class BaseApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@BaseApplication)
            modules(
                listOf(
                    networkModule,
                    localDataSourceModule,
                    remoteDataSourceModule,
                    repositoryModule,
                    viewmodelModule
                )
            )
        }
    }
}