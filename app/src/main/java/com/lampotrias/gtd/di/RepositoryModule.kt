package com.lampotrias.gtd.di

import com.lampotrias.gtd.data.ProjectsRepositoryImpl
import com.lampotrias.gtd.data.TaskRepositoryImpl
import com.lampotrias.gtd.domain.ProjectsRepository
import com.lampotrias.gtd.domain.mappers.ProjectMapper
import org.koin.dsl.module

val repositoryModule = module {
	single { TaskRepositoryImpl(get()) }
//	single { TagRepository(get()) }
//	single { TagTypeRepository(get()) }
	single<ProjectsRepository> { ProjectsRepositoryImpl(get(), get(), get()) }
	single { ProjectMapper() }
}