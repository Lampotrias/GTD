package com.lampotrias.gtd.di

import com.lampotrias.gtd.data.ProjectsRepositoryImpl
import com.lampotrias.gtd.data.TaskRepositoryImpl
import com.lampotrias.gtd.domain.ProjectsRepository
import com.lampotrias.gtd.domain.mappers.ProjectMapper
import com.lampotrias.gtd.domain.mappers.TagTypeMapper
import com.lampotrias.gtd.domain.mappers.TasksMapper
import org.koin.dsl.module

val repositoryModule = module {
    single { TaskRepositoryImpl(get(), get(), get()) }
//	single { TagRepository(get()) }
//	single { TagTypeRepository(get()) }
    single<ProjectsRepository> { ProjectsRepositoryImpl(get(), get(), get()) }
    single { ProjectMapper() }
    single { TasksMapper(get(), get()) }
    single { TagTypeMapper() }
}