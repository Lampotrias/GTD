package com.lampotrias.gtd.di

import com.lampotrias.gtd.MainViewModel
import com.lampotrias.gtd.ui.addtask.TaskAddUpdateViewModel
import com.lampotrias.gtd.ui.inbox.InputBoxViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelsModule = module {
    viewModel { MainViewModel(get(), get()) }
    viewModel { InputBoxViewModel(get(), get(), get()) }
    viewModel { TaskAddUpdateViewModel(get(), get()) }
}