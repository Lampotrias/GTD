package com.lampotrias.gtd.ui.listprojectselector.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.lampotrias.gtd.R
import com.lampotrias.gtd.tools.OnClickCooldownListener
import com.lampotrias.gtd.ui.listprojectselector.ListProjectAdapterModel

class ListProjectsSelectorAdapter(
    private val onItemClick: (item: ListProjectAdapterModel) -> Unit,
) : RecyclerView.Adapter<ListProjectsSelectorViewHolder>() {
    private val tasks = mutableListOf<ListProjectAdapterModel>()

    fun setItems(tasks: List<ListProjectAdapterModel>) {
        val productDiffResult =
            DiffUtil.calculateDiff(ListProjectsSelectorDiffUtil(this.tasks, tasks))

        this.tasks.clear()
        this.tasks.addAll(tasks)

        productDiffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListProjectsSelectorViewHolder {
        return ListProjectsSelectorViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.list_project_item_vh, parent, false),
        )
    }

    override fun getItemCount(): Int {
        return tasks.size
    }

    override fun onBindViewHolder(holder: ListProjectsSelectorViewHolder, position: Int) {
        holder.itemView.setOnClickListener(OnClickCooldownListener {
            onItemClick.invoke(tasks[position])
        })
        holder.bind(tasks[position])
    }
}