package com.lampotrias.gtd.domain.model

import com.lampotrias.gtd.data.database.projects.ProjectEntity

data class ProjectDomainModel(
	val id: Long,
	val name: String,
	val iconName: String,
) {
	fun toEntity(): ProjectEntity {
		return ProjectEntity(
			id = id,
			name = name,
			iconName = iconName
		)
	}
}