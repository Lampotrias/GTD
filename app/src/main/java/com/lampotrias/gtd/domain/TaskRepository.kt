package com.lampotrias.gtd.domain

import com.lampotrias.gtd.domain.model.TaskAddUpdateModel
import com.lampotrias.gtd.domain.model.TaskDomainModel
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    fun getAllTasks(): Flow<List<TaskDomainModel>>

    fun getTasksByList(list: String): Flow<List<TaskDomainModel>>

    fun getTasksByListAndProject(
        list: String,
        projectId: Long,
    ): Flow<List<TaskDomainModel>>

    suspend fun updateTaskComplete(
        taskId: Long,
        isCompleted: Boolean,
    )

    fun getTaskById(taskId: Long): Flow<TaskDomainModel?>

    suspend fun updateTask(taskAddUpdateModel: TaskAddUpdateModel)

    suspend fun insertTask(taskAddUpdateModel: TaskAddUpdateModel)
}
