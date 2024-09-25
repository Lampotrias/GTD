package com.lampotrias.gtd.domain

import com.lampotrias.gtd.domain.model.TaskDomainModel
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
	suspend fun getAllTasks(): Flow<List<TaskDomainModel>>
	suspend fun getTaskById(taskId: Long): TaskDomainModel?
	suspend fun insertTask(id: Long, name: String, projectId: Long?, tagIds: List<Long>, description: String, list: String)
}