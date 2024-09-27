package com.lampotrias.gtd.ui.inbox

import com.lampotrias.gtd.domain.model.TaskDomainModel

interface TaskEventListener {
    fun onTaskClick(task: TaskDomainModel) = Unit
    fun onTaskCompleteChange(task: TaskDomainModel) = Unit
    fun onTaskFavoriteClick(task: TaskDomainModel) = Unit
}