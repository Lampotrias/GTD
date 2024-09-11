package com.lampotrias.gtd.di

import org.koin.dsl.module

val appModule = module {
    single<DispatcherProvider> { DefaultDispatcherProvider() }
}