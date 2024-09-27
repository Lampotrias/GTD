package com.lampotrias.gtd.ui.inbox.adapter

import com.lampotrias.gtd.domain.model.TaskDomainModel

class TaskDiffUtil(
    private val oldTasks: MutableList<TaskDomainModel>,
    private val newTasks: List<TaskDomainModel>
) : androidx.recyclerview.widget.DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldTasks.size
    }

    override fun getNewListSize(): Int {
        return newTasks.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldTasks[oldItemPosition].id == newTasks[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldTasks[oldItemPosition] == newTasks[newItemPosition]
    }


}
