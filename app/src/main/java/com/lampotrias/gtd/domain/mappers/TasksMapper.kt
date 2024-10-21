package com.lampotrias.gtd.domain.mappers

import com.lampotrias.gtd.data.database.tagtypes.TagTypeEntity.Companion.CUSTOM_TAG_TYPE_ID
import com.lampotrias.gtd.data.database.tagtypes.TagTypeEntity.Companion.ENERGY_TAG_TYPE_ID
import com.lampotrias.gtd.data.database.tagtypes.TagTypeEntity.Companion.PRIORITY_TAG_TYPE_ID
import com.lampotrias.gtd.data.database.tagtypes.TagTypeEntity.Companion.TIME_TAG_TYPE_ID
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
            customTags =
                entity.tags.filter { it.tagType.id == CUSTOM_TAG_TYPE_ID }.map {
                    TagDomainModel(
                        id = it.tag.id,
                        name = it.tag.name,
                        iconName = it.tag.iconName,
                        type = tagTypeMapper.toModel(it.tagType),
                    )
                },
            priority =
                entity.tags
                    .filter { it.tagType.id == PRIORITY_TAG_TYPE_ID }
                    .map {
                        TagDomainModel(
                            id = it.tag.id,
                            name = it.tag.name,
                            iconName = it.tag.iconName,
                            type = tagTypeMapper.toModel(it.tagType),
                        )
                    }.firstOrNull(),
            time =
                entity.tags
                    .filter { it.tagType.id == TIME_TAG_TYPE_ID }
                    .map {
                        TagDomainModel(
                            id = it.tag.id,
                            name = it.tag.name,
                            iconName = it.tag.iconName,
                            type = tagTypeMapper.toModel(it.tagType),
                        )
                    }.firstOrNull(),
            energy =
                entity.tags
                    .filter { it.tagType.id == ENERGY_TAG_TYPE_ID }
                    .map {
                        TagDomainModel(
                            id = it.tag.id,
                            name = it.tag.name,
                            iconName = it.tag.iconName,
                            type = tagTypeMapper.toModel(it.tagType),
                        )
                    }.firstOrNull(),
            list = entity.taskEntity.list,
            description = entity.taskEntity.description,
            isCompleted = entity.taskEntity.isCompleted,
            notificationTime = entity.taskEntity.notificationTime,
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
                    notificationTime = model.notificationTime,
                ),
            projectEntity = model.project?.let { projectMapper.toEntity(it) },
            tags = emptyList(),
        )
}
