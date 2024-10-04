package com.lampotrias.gtd.data

import com.lampotrias.gtd.data.database.projects.ProjectDao
import com.lampotrias.gtd.di.DispatcherProvider
import com.lampotrias.gtd.domain.ProjectsRepository
import com.lampotrias.gtd.domain.mappers.ProjectMapper
import com.lampotrias.gtd.domain.mappers.ProjectTaskMapper
import com.lampotrias.gtd.domain.model.ProjectDomainModel
import com.lampotrias.gtd.domain.model.ProjectWithTasksDomainModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class ProjectsRepositoryImpl(
    private val projectDao: ProjectDao,
    private val projectMapper: ProjectMapper,
    private val projectTaskMapper: ProjectTaskMapper,
    private val dispatcherProvider: DispatcherProvider,
) : ProjectsRepository {
    override suspend fun addProject(projectDomainModel: ProjectDomainModel) {
        withContext(dispatcherProvider.io) {
            projectDao.addProject(projectMapper.toEntity(projectDomainModel))
        }
    }

    override fun getAllProjects(): Flow<List<ProjectDomainModel>> =
        projectDao.getAllProjects().map {
            it.map { entity ->
                projectMapper.toModel(entity)
            }
        }

    override fun getProjectById(projectId: Long): Flow<ProjectDomainModel?> =
        projectDao.getProjectById(projectId).map { entity ->
            entity?.let { projectMapper.toModel(it) }
        }

    override fun getAllProjectsWithTasks(): Flow<List<ProjectWithTasksDomainModel>> =
        projectDao.getAllProjectsWithTasks().map {
            it.map { entity ->
                projectTaskMapper.toModel(entity)
            }
        }

    override fun getAllProjectByIdWithTasks(projectId: Long): Flow<ProjectWithTasksDomainModel?> =
        projectDao.getAllProjectByIdWithTasks(projectId).map { entity ->
            projectTaskMapper.toModel(entity)
        }
}
