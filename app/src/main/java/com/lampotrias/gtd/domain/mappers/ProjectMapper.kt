package com.lampotrias.gtd.domain.mappers

import com.lampotrias.gtd.data.database.projects.ProjectEntity
import com.lampotrias.gtd.domain.model.ProjectDomainModel

class ProjectMapper : DataDomainMapper<ProjectDomainModel, ProjectEntity> {
    override fun toModel(entity: ProjectEntity): ProjectDomainModel =
        ProjectDomainModel(
            id = entity.id,
            name = entity.name,
            iconName = entity.iconName,
        )

    override fun toEntity(model: ProjectDomainModel): ProjectEntity =
        ProjectEntity(
            id = model.id,
            name = model.name,
            iconName = model.iconName,
        )
}
