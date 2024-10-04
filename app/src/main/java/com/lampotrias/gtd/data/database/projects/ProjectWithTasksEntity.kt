package com.lampotrias.gtd.data.database.projects

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation
import com.lampotrias.gtd.data.database.tasks.TaskEntity
import com.lampotrias.gtd.data.database.tasks.TaskWithTagsEntity

@Entity(tableName = "projects")
data class ProjectWithTasksEntity(
    @Embedded
    val project: ProjectEntity,
    @Relation(
        entity = TaskEntity::class,
        parentColumn = "id",
        entityColumn = "project_id",
    )
    val tasks: List<TaskWithTagsEntity>,
)
