package com.lampotrias.gtd.ui.projects.adapter

import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.lampotrias.gtd.R
import com.lampotrias.gtd.domain.model.ProjectDomainModel
import com.lampotrias.gtd.tools.OnClickCooldownListener
import com.lampotrias.gtd.ui.projects.ProjectEventListener

class ProjectItemViewHolder(
    itemView: View,
    private val listener: ProjectEventListener,
) : RecyclerView.ViewHolder(itemView) {
    private val tasNameView = itemView.findViewById<AppCompatTextView>(R.id.project_name)

    fun bind(task: ProjectDomainModel) {
        tasNameView.text = task.name

        itemView.setOnClickListener(
            OnClickCooldownListener {
                listener.onProjectClick(task)
            },
        )
    }
}
