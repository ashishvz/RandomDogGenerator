package com.ashishvz.dogimage.di

import com.ashishvz.dogimage.data.remote.RemoteDataSource
import com.ashishvz.dogimage.data.remote.RemoteDataSourceImpl
import org.koin.dsl.module

val remoteDataSourceModule = module {
    single<RemoteDataSource> {
        RemoteDataSourceImpl(get())
    }
}