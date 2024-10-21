package com.lampotrias.gtd.domain.mappers

import com.lampotrias.gtd.data.database.projects.ProjectWithTasksEntity
import com.lampotrias.gtd.data.database.tags.TagEntityWithType
import com.lampotrias.gtd.data.database.tagtypes.TagTypeEntity.Companion.ENERGY_TAG_TYPE_ID
import com.lampotrias.gtd.data.database.tagtypes.TagTypeEntity.Companion.PRIORITY_TAG_TYPE_ID
import com.lampotrias.gtd.data.database.tagtypes.TagTypeEntity.Companion.TIME_TAG_TYPE_ID
import com.lampotrias.gtd.data.database.tasks.TaskWithTagsEntity
import com.lampotrias.gtd.domain.model.ProjectDomainModel
import com.lampotrias.gtd.domain.model.ProjectWithTasksDomainModel
import com.lampotrias.gtd.domain.model.TagDomainModel
import com.lampotrias.gtd.domain.model.TaskDomainModel

class ProjectTaskMapper(
    private val projectMapper: ProjectMapper,
    private val tagTypeMapper: TagTypeMapper,
) : DataDomainMapper<ProjectWithTasksDomainModel, ProjectWithTasksEntity> {
    override fun toModel(entity: ProjectWithTasksEntity): ProjectWithTasksDomainModel {
        val projectDomainModel = projectMapper.toModel(entity.project)
        return ProjectWithTasksDomainModel(
            id = entity.project.id,
            name = entity.project.name,
            iconName = entity.project.iconName,
            tasks = taskToDomainModel(entity, projectDomainModel),
        )
    }

    private fun taskToDomainModel(
        entity: ProjectWithTasksEntity,
        projectDomainModel: ProjectDomainModel,
    ) = entity.tasks.map { taskEntity ->
        TaskDomainModel(
            id = taskEntity.taskEntity.id,
            name = taskEntity.taskEntity.name,
            project = projectDomainModel,
            customTags = tagToDomainModels(taskEntity),
            list = taskEntity.taskEntity.list,
            description = taskEntity.taskEntity.description,
            isCompleted = taskEntity.taskEntity.isCompleted,
            time =
                taskEntity.tags
                    .firstOrNull { it.tagType.id == TIME_TAG_TYPE_ID }
                    ?.let { tagToDomainModel(it) },
            priority =
                taskEntity.tags
                    .firstOrNull { it.tagType.id == PRIORITY_TAG_TYPE_ID }
                    ?.let { tagToDomainModel(it) },
            energy =
                taskEntity.tags
                    .firstOrNull { it.tagType.id == ENERGY_TAG_TYPE_ID }
                    ?.let { tagToDomainModel(it) },
            notificationTime = taskEntity.taskEntity.notificationTime,
        )
    }

    private fun tagToDomainModels(taskEntity: TaskWithTagsEntity) =
        taskEntity.tags.map {
            tagToDomainModel(it)
        }

    private fun tagToDomainModel(entity: TagEntityWithType) =
        TagDomainModel(
            id = entity.tag.id,
            name = entity.tag.name,
            iconName = entity.tag.iconName,
            type = tagTypeMapper.toModel(entity.tagType),
        )

    override fun toEntity(model: ProjectWithTasksDomainModel): ProjectWithTasksEntity {
        TODO("Not yet implemented")
    }
}
