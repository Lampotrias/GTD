package com.lampotrias.gtd.domain

import com.lampotrias.gtd.domain.model.TaskDomainModel
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
	fun getAllTasks(): Flow<List<TaskDomainModel>>
	suspend fun updateTaskComplete(taskId: Long, isCompleted: Boolean)
	fun getTaskById(taskId: Long): Flow<TaskDomainModel?>
	suspend fun insertTask(id: Long, name: String, projectId: Long?, tagIds: List<Long>, description: String, list: String)
}