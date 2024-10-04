package com.lampotrias.gtd.ui.projects.adapter

import com.lampotrias.gtd.domain.model.ProjectWithTasksDomainModel

class ProjectsListDiffUtil(
    private val oldTasks: MutableList<ProjectWithTasksDomainModel>,
    private val newTasks: List<ProjectWithTasksDomainModel>,
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
