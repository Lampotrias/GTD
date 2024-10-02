package com.lampotrias.gtd.ui.listprojectselector.adapter

import com.lampotrias.gtd.ui.listprojectselector.ListProjectAdapterModel

class ListProjectsSelectorDiffUtil(
    private val oldTasks: MutableList<ListProjectAdapterModel>,
    private val newTasks: List<ListProjectAdapterModel>,
) : androidx.recyclerview.widget.DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldTasks.size

    override fun getNewListSize(): Int = newTasks.size

    override fun areItemsTheSame(
        oldItemPosition: Int,
        newItemPosition: Int,
    ): Boolean =
        oldTasks[oldItemPosition].id == newTasks[newItemPosition].id &&
            oldTasks[oldItemPosition].code == newTasks[newItemPosition].code

    override fun areContentsTheSame(
        oldItemPosition: Int,
        newItemPosition: Int,
    ): Boolean = oldTasks[oldItemPosition] == newTasks[newItemPosition]
}
