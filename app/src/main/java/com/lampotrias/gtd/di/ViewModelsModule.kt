package com.lampotrias.gtd.di

import com.lampotrias.gtd.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelsModule = module {
	viewModel { MainViewModel(get(), get()) }
}