package com.lampotrias.gtd.data.database.tasks

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update

@Dao
interface TaskDao {

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insertTask(task: TaskEntity): Long

	@Update
	suspend fun updateTask(task: TaskEntity)

	@Delete
	suspend fun deleteTask(task: TaskEntity)

	@Query("SELECT * FROM tasks WHERE task_id = :taskId")
	suspend fun getTaskById(taskId: Long): TaskEntity?

	@Transaction
	@Query("SELECT * FROM tasks WHERE task_id = :taskId")
	suspend fun getTaskWithTagsById(taskId: Long): TaskWithTagsAndProject?
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