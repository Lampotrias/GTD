package com.lampotrias.gtd.domain.mappers

import com.lampotrias.gtd.data.database.projects.ProjectWithTasksEntity
import com.lampotrias.gtd.domain.model.ProjectWithTasksDomainModel

class ProjectTaskMapper(
    private val tasksMapper: TasksMapper,
) {
    fun toModel(entity: ProjectWithTasksEntity): ProjectWithTasksDomainModel =
        ProjectWithTasksDomainModel(
            id = entity.project.id,
            name = entity.project.name,
            iconName = entity.project.iconName,
            tasks = entity.tasks.map { tasksMapper.toModel(it) },
        )
}
