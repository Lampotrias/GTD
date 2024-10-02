package com.lampotrias.gtd.ui.projects.adapter

import com.lampotrias.gtd.domain.model.ProjectDomainModel

class ProjectsListDiffUtil(
    private val oldTasks: MutableList<ProjectDomainModel>,
    private val newTasks: List<ProjectDomainModel>,
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
