package com.lampotrias.gtd.domain.model

data class TaskDomainModel(
    val id: Long,
    val name: String,
    val project: ProjectDomainModel?,
    val tags: List<TagDomainModel>,
    val list: String,
    val description: String,
    val isCompleted: Boolean,
)
