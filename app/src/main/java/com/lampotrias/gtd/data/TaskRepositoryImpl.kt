package com.lampotrias.gtd.data

import com.lampotrias.gtd.data.database.tasks.TaskDao
import com.lampotrias.gtd.data.database.tasks.TaskEntity
import com.lampotrias.gtd.data.database.tasks.TasksTagsCrossRef
import com.lampotrias.gtd.di.DispatcherProvider
import com.lampotrias.gtd.domain.TaskRepository
import com.lampotrias.gtd.domain.mappers.TasksMapper
import com.lampotrias.gtd.domain.model.TaskDomainModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TaskRepositoryImpl(
    private val taskDao: TaskDao,
    private val tasksMapper: TasksMapper,
    private val dispatcherProvider: DispatcherProvider,
) : TaskRepository {

    override suspend fun insertTask(
        id: Long,
        name: String,
        projectId: Long?,
        tagIds: List<Long>,
        description: String,
        list: String
    ) {
        val taskEntity = TaskEntity(
            id = id,
            name = name,
            projectId = projectId,
            description = description,
            list = list
        )
        // Вставка задачи в базу данных
        taskDao.insertTask(taskEntity)

        // Связываем задачу с тегами
        tagIds.forEach { tagId ->
            val crossRef = TasksTagsCrossRef(
                taskEntityId = taskEntity.id,
                tagId = tagId
            )
            // Вставка связей между задачей и тегами
            taskDao.insertTaskTagCrossRef(crossRef)
        }
    }

    override suspend fun getTaskById(taskId: Long): TaskDomainModel? {
        val taskWithTags = taskDao.getTaskWithTagsById(taskId) ?: return null

        return tasksMapper.toModel(taskWithTags)
    }

    override suspend fun getAllTasks(): Flow<List<TaskDomainModel>> {
        return taskDao.getAllTasksWithTags().map { taskWithTags ->
            taskWithTags.map { tasksMapper.toModel(it) }
        }
    }
}