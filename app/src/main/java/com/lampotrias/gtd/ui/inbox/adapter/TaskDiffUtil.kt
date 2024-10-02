package com.lampotrias.gtd.ui.inbox.adapter

import com.lampotrias.gtd.domain.model.TaskDomainModel

class TaskDiffUtil(
    private val oldTasks: MutableList<TaskDomainModel>,
    private val newTasks: List<TaskDomainModel>,
) : androidx.recyclerview.widget.DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldTasks.size

    override fun getNewListSize(): Int = newTasks.size

    override fun areItemsTheSame(
        oldItemPosition: Int,
        newItemPosition: Int,
    ): Boolean = oldTasks[oldItemPosition].id == newTasks[newItemPosition].id

    override fun areContentsTheSame(
        oldItemPosition: Int,
        newItemPosition: Int,
    ): Boolean = oldTasks[oldItemPosition] == newTasks[newItemPosition]
}
