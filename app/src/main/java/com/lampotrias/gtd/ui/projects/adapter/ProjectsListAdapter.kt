package com.lampotrias.gtd.ui.projects.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.lampotrias.gtd.R
import com.lampotrias.gtd.domain.model.ProjectWithTasksDomainModel
import com.lampotrias.gtd.ui.projects.ProjectEventListener

class ProjectsListAdapter(
    private val listener: ProjectEventListener,
) : RecyclerView.Adapter<ProjectItemViewHolder>() {
    private val tasks = mutableListOf<ProjectWithTasksDomainModel>()

    fun setTasks(tasks: List<ProjectWithTasksDomainModel>) {
        val productDiffResult = DiffUtil.calculateDiff(ProjectsListDiffUtil(this.tasks, tasks))

        this.tasks.clear()
        this.tasks.addAll(tasks)

        productDiffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ProjectItemViewHolder =
        ProjectItemViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.project_item_vh, parent, false),
            listener,
        )

    override fun getItemCount(): Int = tasks.size

    override fun onBindViewHolder(
        holder: ProjectItemViewHolder,
        position: Int,
    ) {
        holder.bind(tasks[position])
    }
}
