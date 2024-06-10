package com.sdk.fenigepaytool.di

import com.sdk.fenigepaytool.repository.PaytoolRepository
import org.koin.dsl.module
import org.koin.dsl.single

internal val repositoryModule = module {

    single<PaytoolRepository>()
}