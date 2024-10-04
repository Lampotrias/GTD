package com.lampotrias.gtd.domain

import com.lampotrias.gtd.domain.model.ProjectDomainModel
import com.lampotrias.gtd.domain.model.ProjectWithTasksDomainModel
import kotlinx.coroutines.flow.Flow

interface ProjectsRepository {
    suspend fun addProject(projectDomainModel: ProjectDomainModel)

    fun getAllProjects(): Flow<List<ProjectDomainModel>>

    fun getProjectById(projectId: Long): Flow<ProjectDomainModel?>

    fun getAllProjectsWithTasks(): Flow<List<ProjectWithTasksDomainModel>>

    fun getAllProjectByIdWithTasks(projectId: Long): Flow<ProjectWithTasksDomainModel?>
}
