package com.lampotrias.gtd.di

import com.lampotrias.gtd.data.ProjectsRepositoryImpl
import com.lampotrias.gtd.data.TagsRepositoryImpl
import com.lampotrias.gtd.data.TaskRepositoryImpl
import com.lampotrias.gtd.domain.ProjectsRepository
import com.lampotrias.gtd.domain.TagsRepository
import com.lampotrias.gtd.domain.TaskRepository
import com.lampotrias.gtd.domain.mappers.ProjectMapper
import com.lampotrias.gtd.domain.mappers.TagTypeMapper
import com.lampotrias.gtd.domain.mappers.TasksMapper
import com.lampotrias.gtd.domain.usecases.GetCustomTagsUseCase
import org.koin.dsl.module

val repositoryModule = module {
    single<TaskRepository> { TaskRepositoryImpl(get(), get(), get()) }
    single<TagsRepository> { TagsRepositoryImpl(get(), get(), get()) }
    single { GetCustomTagsUseCase(get(), get()) }
    // single { TagRepository(get()) }
    // single { TagTypeRepository(get()) }
    single<ProjectsRepository> { ProjectsRepositoryImpl(get(), get(), get()) }
    single { ProjectMapper() }
    single { TasksMapper(get(), get()) }
    single { TagTypeMapper() }
}
