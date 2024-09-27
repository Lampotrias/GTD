package com.lampotrias.gtd.ui.inbox.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.lampotrias.gtd.R
import com.lampotrias.gtd.domain.model.TaskDomainModel
import com.lampotrias.gtd.ui.inbox.TaskEventListener

class TaskAdapter(
    private val listener: TaskEventListener,
) : RecyclerView.Adapter<TaskViewHolder>() {
    private val tasks = mutableListOf<TaskDomainModel>()

    fun setTasks(tasks: List<TaskDomainModel>) {
        val productDiffResult = DiffUtil.calculateDiff(TaskDiffUtil(this.tasks, tasks))

        this.tasks.clear()
        this.tasks.addAll(tasks)

        productDiffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.task_item_vh, parent, false),
            listener
        )
    }

    override fun getItemCount(): Int {
        return tasks.size
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(tasks[position])
    }

}