package com.lampotrias.gtd.data

import com.lampotrias.gtd.data.database.projects.ProjectDao
import com.lampotrias.gtd.di.DispatcherProvider
import com.lampotrias.gtd.domain.ProjectsRepository
import com.lampotrias.gtd.domain.mappers.ProjectMapper
import com.lampotrias.gtd.domain.model.ProjectDomainModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class ProjectsRepositoryImpl(
    private val projectDao: ProjectDao,
    private val projectMapper: ProjectMapper,
    private val dispatcherProvider: DispatcherProvider,
) : ProjectsRepository {
    override suspend fun addProject(projectDomainModel: ProjectDomainModel) {
        withContext(dispatcherProvider.io) {
            projectDao.addProject(projectMapper.toEntity(projectDomainModel))
        }
    }

    override suspend fun getAllProjects(): Flow<List<ProjectDomainModel>> {
        return withContext(dispatcherProvider.io) {
            projectDao.getAllProjects()
        }.map {
            it.map { entity ->
                projectMapper.toModel(entity)
            }
        }
    }
}