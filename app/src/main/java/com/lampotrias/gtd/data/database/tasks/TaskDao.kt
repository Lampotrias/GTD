package com.lampotrias.gtd.data.database.tasks

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: TaskEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateTask(task: TaskEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTagCrossRef(crossRef: TasksTagsCrossRef)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSubtasksCrossRef(crossRef: TasksSubTasksCrossRef)

    @Query("DELETE FROM tasks_tags WHERE task_id = :taskId")
    suspend fun deleteTagCrossRef(taskId: Long)

    @Transaction
    suspend fun insertTaskWithTags(
        task: TaskEntity,
        tagIds: List<Long>,
    ) {
        val taskId = insertTask(task)

        tagIds.forEach { tagId ->
            val crossRef = TasksTagsCrossRef(taskEntityId = taskId, tagId = tagId)
            insertTagCrossRef(crossRef)
        }
    }

    @Query("SELECT * FROM tasks WHERE task_id = :taskId")
    suspend fun getTaskById(taskId: Long): TaskEntity?

    @Transaction
    @Query("SELECT * FROM tasks WHERE task_id = :taskId")
    fun getTaskWithTagsById(taskId: Long): Flow<TaskWithTagsAndProjectAndSubtasksEntity?>

    @Transaction
    @Query("SELECT * FROM tasks")
    fun getAllTasksWithTags(): Flow<List<TaskWithTagsAndProjectAndSubtasksEntity>>

    @Transaction
    @Query("SELECT * FROM tasks WHERE list = :list")
    fun selectTasksInList(list: String): Flow<List<TaskWithTagsAndProjectAndSubtasksEntity>>

    @Transaction
    @Query("SELECT * FROM tasks WHERE project_id = :projectId")
    fun selectTasksInProject(projectId: Long): Flow<List<TaskWithTagsAndProjectAndSubtasksEntity>>

    @Query("UPDATE tasks SET is_completed = :completed WHERE task_id = :taskId")
    fun updateTaskComplete(
        taskId: Long,
        completed: Boolean,
    )

    @Transaction
    @Query("SELECT * FROM tasks WHERE list = :list")
    fun getTasksByList(list: String): Flow<List<TaskWithTagsAndProjectAndSubtasksEntity>>

    @Transaction
    @Query("SELECT * FROM tasks WHERE list = :list AND project_id = :projectId")
    fun getTasksByListAndProject(
        list: String,
        projectId: Long,
    ): Flow<List<TaskWithTagsAndProjectAndSubtasksEntity>>
}
