package com.lampotrias.gtd.data.database.tasks

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.Relation
import com.lampotrias.gtd.data.database.projects.ProjectEntity
import com.lampotrias.gtd.data.database.tags.TagEntity

data class TaskWithTagsAndProject(
	@Embedded
	val taskEntity: TaskEntity,

	@Relation(
		parentColumn = "task_id",
		entityColumn = "tag_id",
		associateBy = Junction(TasksTagsCrossRef::class)
	)
	val tags: List<TagEntity>,

	@Relation(
		parentColumn = "project_id",
		entityColumn = "id"
	)
	val projectEntity: ProjectEntity?
)

@Entity(primaryKeys = ["task_id", "tag_id"])
data class TasksTagsCrossRef(
	@ColumnInfo(name = "task_id")
	val taskEntityId: Long,
	@ColumnInfo(name = "tag_id")
	val tagId: Long
)