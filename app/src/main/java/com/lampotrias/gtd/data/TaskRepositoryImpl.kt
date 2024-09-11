package com.lampotrias.gtd.data

import com.lampotrias.gtd.data.database.tasks.TaskDao
import com.lampotrias.gtd.data.database.tasks.TaskEntity
import com.lampotrias.gtd.domain.TaskRepository
import com.lampotrias.gtd.domain.model.TaskDomainModel

class TaskRepositoryImpl(private val taskDao: TaskDao) : TaskRepository {

	override suspend fun insertTask(
		id: Long,
		name: String,
		projectId: Long?,
		tagIds: List<Long>,
	) {
		// Сначала вставляем задачу в таблицу задач
		val taskEntity = TaskEntity(
			id = id,
			name = name,
			projectId = projectId
		)
		val taskId = taskDao.insertTask(taskEntity)

		// Затем вставляем соответствующие записи в таблицу связей TaskTagCrossRef
		tagIds.forEach { tagId ->
//			val crossRef = TaskTagCrossRef(taskId = taskId, tagId = tagId)
//			taskDao.insertTaskTagCrossRef(crossRef)
		}
	}

	override suspend fun getTaskById(taskId: Long): TaskDomainModel? {
//		val taskWithTags = taskDao.getTaskWithTagsById(taskId) ?: return null
//
//		return TaskDomainModel.fromEntity(taskWithTags)
		return null
	}

	override suspend fun getAllTasks(): List<TaskDomainModel> {
//		return taskDao.getAllTasksWithTags().map { taskWithTags ->
//			TaskDomainModel.fromEntity(taskWithTags)
//		}
		return emptyList()
	}
}