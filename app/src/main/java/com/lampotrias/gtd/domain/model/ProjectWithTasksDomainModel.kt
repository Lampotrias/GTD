package com.lampotrias.gtd.domain.model

data class ProjectWithTasksDomainModel(
    val id: Long,
    val name: String,
    val iconName: String,
    val tasks: List<TaskDomainModel>,
)
