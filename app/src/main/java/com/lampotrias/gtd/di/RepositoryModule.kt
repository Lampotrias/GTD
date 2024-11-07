package com.lampotrias.gtd.di

import com.lampotrias.gtd.data.DataTimeRepositoryImpl
import com.lampotrias.gtd.data.ProjectsRepositoryImpl
import com.lampotrias.gtd.data.TagsRepositoryImpl
import com.lampotrias.gtd.data.TaskRepositoryImpl
import com.lampotrias.gtd.domain.DataTimeRepository
import com.lampotrias.gtd.domain.ProjectsRepository
import com.lampotrias.gtd.domain.TagsRepository
import com.lampotrias.gtd.domain.TaskRepository
import com.lampotrias.gtd.domain.mappers.ProjectMapper
import com.lampotrias.gtd.domain.mappers.ProjectTaskMapper
import com.lampotrias.gtd.domain.mappers.TagTypeMapper
import com.lampotrias.gtd.domain.mappers.TasksMapper
import com.lampotrias.gtd.domain.usecases.GetAvailableNotifyDateTimeUseCase
import com.lampotrias.gtd.domain.usecases.GetCustomTagsUseCase
import com.lampotrias.gtd.domain.usecases.GetEnergyTagsUseCase
import com.lampotrias.gtd.domain.usecases.GetInboxTasksUseCase
import com.lampotrias.gtd.domain.usecases.GetListsUseCase
import com.lampotrias.gtd.domain.usecases.GetNextTasksUseCase
import com.lampotrias.gtd.domain.usecases.GetPriorityTagsUseCase
import com.lampotrias.gtd.domain.usecases.GetTaskByIdUseCase
import com.lampotrias.gtd.domain.usecases.GetTimeTagsUseCase
import com.lampotrias.gtd.domain.usecases.SaveTaskUseCase
import com.lampotrias.gtd.domain.usecases.UpdateTaskCompleteUseCase
import com.lampotrias.gtd.domain.usecases.UpdateTaskUseCase
import com.lampotrias.gtd.ui.datetimeplanner.DataTimeNotificationProvider
import org.koin.dsl.module

val repositoryModule =
    module {
        // Repo
        single<TaskRepository> { TaskRepositoryImpl(get(), get(), get()) }
        single<TagsRepository> { TagsRepositoryImpl(get(), get(), get()) }
        single<ProjectsRepository> { ProjectsRepositoryImpl(get(), get(), get(), get()) }
        single<DataTimeRepository> { DataTimeRepositoryImpl() }

        // UseCases
        single { GetCustomTagsUseCase(get(), get()) }
        single { GetTimeTagsUseCase(get(), get()) }
        single { GetEnergyTagsUseCase(get(), get()) }
        single { GetPriorityTagsUseCase(get(), get()) }
        single { GetListsUseCase(get()) }
        single { GetInboxTasksUseCase(get(), get()) }
        single { GetNextTasksUseCase(get(), get()) }
        single { UpdateTaskCompleteUseCase(get(), get()) }
        single { GetAvailableNotifyDateTimeUseCase(get(), get(), get()) }
        single { SaveTaskUseCase(get(), get(), get()) }
        single { UpdateTaskUseCase(get(), get(), get()) }
        single { GetTaskByIdUseCase(get(), get()) }

        // Mappers
        single { ProjectMapper() }
        single { TasksMapper(get(), get()) }
        single { TagTypeMapper() }
        single { ProjectTaskMapper(get()) }

        factory { DataTimeNotificationProvider() }
    }
