package com.ashishvz.dogimage.di

import com.ashishvz.dogimage.data.repository.DogRepository
import com.ashishvz.dogimage.data.repository.DogRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    single<DogRepository> {
        DogRepositoryImpl(get(), get())
    }
}