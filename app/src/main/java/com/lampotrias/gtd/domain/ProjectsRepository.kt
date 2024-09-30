package com.lampotrias.gtd.domain

import com.lampotrias.gtd.domain.model.ProjectDomainModel
import kotlinx.coroutines.flow.Flow

interface ProjectsRepository {
    suspend fun addProject(projectDomainModel: ProjectDomainModel)
    fun getAllProjects(): Flow<List<ProjectDomainModel>>
}