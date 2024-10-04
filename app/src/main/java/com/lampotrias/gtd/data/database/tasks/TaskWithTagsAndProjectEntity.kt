package com.lampotrias.gtd.data.database.tasks

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.Relation
import com.lampotrias.gtd.data.database.projects.ProjectEntity
import com.lampotrias.gtd.data.database.tags.TagEntity
import com.lampotrias.gtd.data.database.tags.TagEntityWithType

data class TaskWithTagsAndProjectEntity(
    @Embedded
    val taskEntity: TaskEntity,
    @Relation(
        entity = TagEntity::class,
        parentColumn = "task_id",
        entityColumn = "tag_id",
        associateBy = Junction(TasksTagsCrossRef::class),
    )
    val tags: List<TagEntityWithType>,
    @Relation(
        parentColumn = "project_id",
        entityColumn = "id",
    )
    val projectEntity: ProjectEntity?,
)

data class TaskWithTagsEntity(
    @Embedded
    val taskEntity: TaskEntity,
    @Relation(
        entity = TagEntity::class,
        parentColumn = "task_id",
        entityColumn = "tag_id",
        associateBy = Junction(TasksTagsCrossRef::class),
    )
    val tags: List<TagEntityWithType>,
)

@Entity(
    tableName = "tasks_tags",
    primaryKeys = ["task_id", "tag_id"],
)
data class TasksTagsCrossRef(
    @ColumnInfo(name = "task_id")
    val taskEntityId: Long,
    @ColumnInfo(name = "tag_id")
    val tagId: Long,
)
