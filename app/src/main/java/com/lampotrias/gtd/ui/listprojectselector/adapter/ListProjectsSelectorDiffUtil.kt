package com.lampotrias.gtd.ui.listprojectselector.adapter

import com.lampotrias.gtd.ui.listprojectselector.ListProjectAdapterModel

class ListProjectsSelectorDiffUtil(
    private val oldTasks: MutableList<ListProjectAdapterModel>,
    private val newTasks: List<ListProjectAdapterModel>
) : androidx.recyclerview.widget.DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldTasks.size
    }

    override fun getNewListSize(): Int {
        return newTasks.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldTasks[oldItemPosition].id == newTasks[newItemPosition].id &&
                oldTasks[oldItemPosition].code == newTasks[newItemPosition].code
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldTasks[oldItemPosition] == newTasks[newItemPosition]
    }


}
