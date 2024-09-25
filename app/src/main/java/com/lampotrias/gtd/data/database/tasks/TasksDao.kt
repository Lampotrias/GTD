package com.lampotrias.gtd.data.database.tasks

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insertTask(task: TaskEntity)

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insertTaskTagCrossRef(crossRef: TasksTagsCrossRef)

	@Update
	suspend fun updateTask(task: TaskEntity)

	@Delete
	suspend fun deleteTask(task: TaskEntity)

	@Query("SELECT * FROM tasks WHERE task_id = :taskId")
	suspend fun getTaskById(taskId: Long): TaskEntity?

	@Transaction
	@Query("SELECT * FROM tasks WHERE task_id = :taskId")
	suspend fun getTaskWithTagsById(taskId: Long): TaskWithTagsAndProjectEntity?

	@Transaction
	@Query("SELECT * FROM tasks")
	fun getAllTasksWithTags(): Flow<List<TaskWithTagsAndProjectEntity>>

	@Transaction
	@Query("SELECT * FROM tasks WHERE list = :list")
	fun selectTasksInList(list: String): Flow<List<TaskWithTagsAndProjectEntity>>

	@Transaction
	@Query("SELECT * FROM tasks WHERE project_id = :projectId")
	fun selectTasksInProject(projectId: Long): Flow<List<TaskWithTagsAndProjectEntity>>
//
//	@Transaction
//	@Query("SELECT * FROM tasks")
//	suspend fun getAllTasksWithTags(): List<TaskWithTags>
//
//	@Insert(onConflict = OnConflictStrategy.REPLACE)
//	suspend fun insertTaskTagCrossRef(crossRef: TaskTagCrossRef)
//
//	@Query("DELETE FROM TaskTagCrossRef WHERE task_id = :taskId")
//	suspend fun deleteTaskTagCrossRefByTaskId(taskId: Long)
//
//	@Query("DELETE FROM TaskTagCrossRef WHERE task_id = :taskId AND tag_id = :tagId")
//	suspend fun deleteTaskTagCrossRef(taskId: Long, tagId: Long)
}