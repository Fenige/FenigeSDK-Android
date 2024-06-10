package com.sdk.fenigepaytool.di

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import org.koin.dsl.module

internal val coreModule = httpModule + viewModelModule + repositoryModule +  module {
    factory { CoroutineScope(Job() + Dispatchers.Default) }
}
