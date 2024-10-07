package com.lampotrias.gtd.domain.model

data class TaskAddUpdateModel(
    val taskId: Long = 0,
    val name: String,
    val projectId: Long?,
    val tagIds: List<Long>,
    val description: String,
    val list: String,
    val isCompleted: Boolean = false,
)
