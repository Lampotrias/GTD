package com.lampotrias.gtd.data.database.tasks

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.Junction
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.lampotrias.gtd.data.database.projects.ProjectEntity
import com.lampotrias.gtd.data.database.tags.TagEntity
import com.lampotrias.gtd.data.database.tags.TagEntityWithTag

@Entity(
	tableName = "tasks",
//	foreignKeys = [
//		ForeignKey(
//			entity = ProjectEntity::class,
//			parentColumns = ["id"],
//			childColumns = ["project_id"],
//			onDelete = ForeignKey.SET_NULL
//		)
//	],
//	indices = [Index(value = ["project_id"])]
)
data class TaskEntity(
	@PrimaryKey(autoGenerate = true)
	@ColumnInfo(name = "task_id")
	val id: Long,

	val name: String,

	@ColumnInfo(name = "project_id")
	val projectId: Long?
)