package com.ashishvz.dogimage.di

import com.ashishvz.dogimage.viewmodel.GenerateImageViewModel
import com.ashishvz.dogimage.viewmodel.LocalImageViewmodel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewmodelModule = module {
    viewModel {
        GenerateImageViewModel(get())
    }

    viewModel {
        LocalImageViewmodel(get())
    }
}