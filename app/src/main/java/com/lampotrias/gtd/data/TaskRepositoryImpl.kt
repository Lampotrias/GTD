package com.lampotrias.gtd.data

import com.lampotrias.gtd.data.database.tasks.TaskDao
import com.lampotrias.gtd.data.database.tasks.TaskEntity
import com.lampotrias.gtd.di.DispatcherProvider
import com.lampotrias.gtd.domain.TaskRepository
import com.lampotrias.gtd.domain.mappers.TasksMapper
import com.lampotrias.gtd.domain.model.TaskAddUpdateModel
import com.lampotrias.gtd.domain.model.TaskDomainModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class TaskRepositoryImpl(
    private val taskDao: TaskDao,
    private val tasksMapper: TasksMapper,
    private val dispatcherProvider: DispatcherProvider,
) : TaskRepository {
    override suspend fun insertTask(taskAddUpdateModel: TaskAddUpdateModel) {
        withContext(dispatcherProvider.io) {
            val taskEntity =
                TaskEntity(
                    name = taskAddUpdateModel.name,
                    projectId = taskAddUpdateModel.projectId,
                    description = taskAddUpdateModel.description,
                    list = taskAddUpdateModel.list,
                )

            if (taskAddUpdateModel.tagIds.isEmpty()) {
                taskDao.insertTask(taskEntity)
            } else {
                taskDao.insertTaskWithTags(taskEntity, taskAddUpdateModel.tagIds)
            }
        }
    }

    override suspend fun updateTask(taskAddUpdateModel: TaskAddUpdateModel) {
        withContext(dispatcherProvider.io) {
            val taskEntity =
                TaskEntity(
                    id = taskAddUpdateModel.taskId,
                    name = taskAddUpdateModel.name,
                    projectId = taskAddUpdateModel.projectId,
                    description = taskAddUpdateModel.description,
                    list = taskAddUpdateModel.list,
                    isCompleted = taskAddUpdateModel.isCompleted,
                )

            taskDao.deleteTagCrossRef(taskAddUpdateModel.taskId)

            if (taskAddUpdateModel.tagIds.isEmpty()) {
                taskDao.insertTask(taskEntity)
            } else {
                taskDao.insertTaskWithTags(taskEntity, taskAddUpdateModel.tagIds)
            }
        }
    }

    override fun getTaskById(taskId: Long): Flow<TaskDomainModel?> =
        taskDao.getTaskWithTagsById(taskId).map { entity ->
            entity?.let { tasksMapper.toModel(entity) }
        }

    override fun getAllTasks(): Flow<List<TaskDomainModel>> =
        taskDao.getAllTasksWithTags().map { taskWithTags ->
            taskWithTags.map { tasksMapper.toModel(it) }
        }

    override suspend fun updateTaskComplete(
        taskId: Long,
        isCompleted: Boolean,
    ) {
        withContext(dispatcherProvider.io) {
            taskDao.updateTaskComplete(taskId, isCompleted)
        }
    }

    override fun getTasksByList(list: String): Flow<List<TaskDomainModel>> =
        taskDao.getTasksByList(list).map { taskWithTags ->
            taskWithTags.map { tasksMapper.toModel(it) }
        }

    override fun getTasksByListAndProject(
        list: String,
        projectId: Long,
    ): Flow<List<TaskDomainModel>> =
        taskDao.getTasksByListAndProject(list, projectId).map { taskWithTags ->
            taskWithTags.map { tasksMapper.toModel(it) }
        }
}
