package com.lampotrias.gtd.domain.model

import kotlinx.datetime.Instant

data class TaskAddUpdateModel(
    val taskId: Long = 0,
    val name: String,
    val projectId: Long?,
    val tagIds: List<Long>,
    val description: String,
    val list: String,
    val isCompleted: Boolean = false,
    val notificationTime: Instant?,
)
