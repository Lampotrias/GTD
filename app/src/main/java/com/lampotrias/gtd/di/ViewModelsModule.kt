package com.lampotrias.gtd.di

import com.lampotrias.gtd.MainViewModel
import com.lampotrias.gtd.ui.addtask.TaskAddUpdateViewModel
import com.lampotrias.gtd.ui.datetimeplanner.DataTimeNotificationPickerViewModel
import com.lampotrias.gtd.ui.inbox.InputBoxViewModel
import com.lampotrias.gtd.ui.listprojectselector.ListProjectsSelectorViewModel
import com.lampotrias.gtd.ui.next.NextListViewModel
import com.lampotrias.gtd.ui.projects.ProjectsListViewModel
import com.lampotrias.gtd.ui.tasksproject.TasksProjectViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelsModule =
    module {
        viewModel { MainViewModel() }
        viewModel { InputBoxViewModel(get(), get(), get()) }
        viewModel { NextListViewModel(get(), get(), get()) }
        viewModel { TasksProjectViewModel(get(), get(), get(), get()) }
        viewModel { DataTimeNotificationPickerViewModel(get(), get(), get()) }
        viewModel {
            TaskAddUpdateViewModel(
                get(),
                get(),
                get(),
                get(),
                get(),
                get(),
                get(),
                get(),
                get(),
                get(),
                get(),
                get(),
            )
        }
        viewModel { ListProjectsSelectorViewModel(get(), get(), get(), get(), get()) }
        viewModel { ProjectsListViewModel(get(), get()) }
    }
