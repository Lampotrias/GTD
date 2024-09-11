package com.lampotrias.gtd.domain.model

data class TaskDomainModel(
    val id: Long,
    val name: String,
    val project: ProjectDomainModel?,
    val tags: List<TagDomainModel>,
) {
    companion object {
//		fun fromEntity(taskWithTags: TaskWithTags): TaskDomainModel {
//			return TaskDomainModel(
//				id = taskWithTags.task.id,
//				name = taskWithTags.task.name,
//				project = taskWithTags.project?.let {
//					ProjectDomainModel(
//						id = taskWithTags.project.id,
//						name = taskWithTags.project.name,
//						iconName = taskWithTags.project.iconName
//					)
//				},
//				tags = taskWithTags.tags.map {
//					TagDomainModel.fromEntity(it)
//				}
//			)
//		}
    }
}

