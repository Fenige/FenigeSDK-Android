package com.sdk.fenigepaytool.di

import com.sdk.fenigepaytool.ui.PaytoolViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val viewModelModule = module {
    viewModel { PaytoolViewModel(get()) }
}