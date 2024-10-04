package com.lampotrias.gtd.domain.mappers

import com.lampotrias.gtd.data.database.projects.ProjectWithTasksEntity
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
            tags = tagToDomainModel(taskEntity),
            list = taskEntity.taskEntity.list,
            description = taskEntity.taskEntity.description,
            isCompleted = taskEntity.taskEntity.isCompleted,
        )
    }

    private fun tagToDomainModel(taskEntity: TaskWithTagsEntity) =
        taskEntity.tags.map {
            TagDomainModel(
                id = it.tag.id,
                name = it.tag.name,
                iconName = it.tag.iconName,
                type = tagTypeMapper.toModel(it.tagType),
            )
        }

    override fun toEntity(model: ProjectWithTasksDomainModel): ProjectWithTasksEntity {
        TODO("Not yet implemented")
    }
}