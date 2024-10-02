package com.lampotrias.gtd.domain.mappers

import com.lampotrias.gtd.data.database.tasks.TaskEntity
import com.lampotrias.gtd.data.database.tasks.TaskWithTagsAndProjectEntity
import com.lampotrias.gtd.domain.model.TagDomainModel
import com.lampotrias.gtd.domain.model.TaskDomainModel

class TasksMapper(
    private val projectMapper: ProjectMapper,
    private val tagTypeMapper: TagTypeMapper,
) : DataDomainMapper<TaskDomainModel, TaskWithTagsAndProjectEntity> {
    override fun toModel(entity: TaskWithTagsAndProjectEntity): TaskDomainModel =
        TaskDomainModel(
            id = entity.taskEntity.id,
            name = entity.taskEntity.name,
            project = entity.projectEntity?.let { projectMapper.toModel(it) },
            tags =
                entity.tags.map {
                    TagDomainModel(
                        id = it.tag.id,
                        name = it.tag.name,
                        iconName = it.tag.iconName,
                        type = tagTypeMapper.toModel(it.tagType),
                    )
                },
            list = entity.taskEntity.list,
            description = entity.taskEntity.description,
            isCompleted = entity.taskEntity.isCompleted,
        )

    override fun toEntity(model: TaskDomainModel): TaskWithTagsAndProjectEntity =
        TaskWithTagsAndProjectEntity(
            taskEntity =
                TaskEntity(
                    id = model.id,
                    name = model.name,
                    list = model.list,
                    projectId = model.project?.id,
                    description = model.description,
                ),
            projectEntity = model.project?.let { projectMapper.toEntity(it) },
            tags = emptyList(),
        )
}
