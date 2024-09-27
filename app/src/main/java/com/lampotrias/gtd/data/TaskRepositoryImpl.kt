package com.lampotrias.gtd.data

import com.lampotrias.gtd.data.database.tasks.TaskDao
import com.lampotrias.gtd.data.database.tasks.TaskEntity
import com.lampotrias.gtd.di.DispatcherProvider
import com.lampotrias.gtd.domain.TaskRepository
import com.lampotrias.gtd.domain.mappers.TasksMapper
import com.lampotrias.gtd.domain.model.TaskDomainModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class TaskRepositoryImpl(
    private val taskDao: TaskDao,
    private val tasksMapper: TasksMapper,
    private val dispatcherProvider: DispatcherProvider,
) : TaskRepository {

    override suspend fun insertTask(
        name: String,
        projectId: Long?,
        tagIds: List<Long>,
        description: String,
        list: String
    ) {
        withContext(dispatcherProvider.io) {
            val taskEntity = TaskEntity(
                name = name,
                projectId = projectId,
                description = description,
                list = list
            )

            if (tagIds.isEmpty()) {
                taskDao.insertTask(taskEntity)

            } else {
                taskDao.insertTaskWithTags(taskEntity, tagIds)
            }
        }
    }

    override fun getTaskById(taskId: Long): Flow<TaskDomainModel?> {
        return taskDao.getTaskWithTagsById(taskId).map { entity ->
            entity?.let { tasksMapper.toModel(entity) }
        }
    }

    override fun getAllTasks(): Flow<List<TaskDomainModel>> {
        return taskDao.getAllTasksWithTags().map { taskWithTags ->
            taskWithTags.map { tasksMapper.toModel(it) }
        }
    }

    override suspend fun updateTaskComplete(taskId: Long, isCompleted: Boolean) {
        withContext(dispatcherProvider.io) {
            taskDao.updateTaskComplete(taskId, isCompleted)
        }
    }
}