package com.lampotrias.gtd.domain.model

import kotlinx.datetime.Instant

data class TaskDomainModel(
    val id: Long,
    val name: String,
    val project: ProjectDomainModel?,
    val customTags: List<TagDomainModel>,
    val time: TagDomainModel?,
    val priority: TagDomainModel?,
    val energy: TagDomainModel?,
    val list: String,
    val subtasks: List<TaskDomainModel>,
    val description: String,
    val isCompleted: Boolean,
    val notificationTime: Instant?,
)
