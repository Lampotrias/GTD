package com.lampotrias.gtd.di

import com.lampotrias.gtd.data.ProjectsRepositoryImpl
import com.lampotrias.gtd.data.TagsRepositoryImpl
import com.lampotrias.gtd.data.TaskRepositoryImpl
import com.lampotrias.gtd.domain.ProjectsRepository
import com.lampotrias.gtd.domain.TagsRepository
import com.lampotrias.gtd.domain.TaskRepository
import com.lampotrias.gtd.domain.mappers.ProjectMapper
import com.lampotrias.gtd.domain.mappers.ProjectTaskMapper
import com.lampotrias.gtd.domain.mappers.TagTypeMapper
import com.lampotrias.gtd.domain.mappers.TasksMapper
import com.lampotrias.gtd.domain.usecases.GetCustomTagsUseCase
import com.lampotrias.gtd.domain.usecases.GetEnergyTagsUseCase
import com.lampotrias.gtd.domain.usecases.GetInboxTasksUseCase
import com.lampotrias.gtd.domain.usecases.GetListsUseCase
import com.lampotrias.gtd.domain.usecases.GetNextTasksUseCase
import com.lampotrias.gtd.domain.usecases.GetPriorityTagsUseCase
import com.lampotrias.gtd.domain.usecases.GetTimeTagsUseCase
import com.lampotrias.gtd.domain.usecases.UpdateTaskCompleteUseCase
import org.koin.dsl.module

val repositoryModule =
    module {
        // Repo
        single<TaskRepository> { TaskRepositoryImpl(get(), get(), get()) }
        single<TagsRepository> { TagsRepositoryImpl(get(), get(), get()) }
        single<ProjectsRepository> { ProjectsRepositoryImpl(get(), get(), get(), get()) }

        // UseCases
        single { GetCustomTagsUseCase(get(), get()) }
        single { GetTimeTagsUseCase(get(), get()) }
        single { GetEnergyTagsUseCase(get(), get()) }
        single { GetPriorityTagsUseCase(get(), get()) }
        single { GetListsUseCase(get()) }
        single { GetInboxTasksUseCase(get(), get()) }
        single { GetNextTasksUseCase(get(), get()) }
        single { UpdateTaskCompleteUseCase(get(), get()) }

        // Mappers
        single { ProjectMapper() }
        single { TasksMapper(get(), get()) }
        single { TagTypeMapper() }
        single { ProjectTaskMapper(get(), get()) }
    }
