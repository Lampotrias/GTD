package com.lampotrias.gtd.ui.tasksproject.adapter

import android.graphics.Color
import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.lampotrias.gtd.R
import com.lampotrias.gtd.domain.model.TaskDomainModel
import com.lampotrias.gtd.tools.DrawableUtils
import com.lampotrias.gtd.tools.OnClickCooldownListener
import com.lampotrias.gtd.tools.changeVisibility
import com.lampotrias.gtd.tools.dpToPx
import com.lampotrias.gtd.ui.TaskEventListener
import com.lampotrias.gtd.ui.views.TagView

class TaskViewHolder(
    itemView: View,
    private val listener: TaskEventListener,
) : RecyclerView.ViewHolder(itemView) {
    private val dp1px = itemView.context.dpToPx(1)
    private val dp8px = itemView.context.dpToPx(8f)

    private val tagsContainer = itemView.findViewById<LinearLayout>(R.id.tags_container)
    private val tasNameView = itemView.findViewById<AppCompatTextView>(R.id.task_name)
    private val checkBoxView = itemView.findViewById<CheckBox>(R.id.checkbox)
    private val favoriteView = itemView.findViewById<ImageView>(R.id.task_favorite)

    fun bind(task: TaskDomainModel) {
        tasNameView.text = task.name

        checkBoxView.isChecked = task.isCompleted

        tagsContainer.removeAllViews()
        task.customTags.forEachIndexed { index, tag ->
            val tagView =
                TagView(itemView.context).apply {
                    text = tag.name
                    background =
                        DrawableUtils.createCustomBackground(
                            backgroundColor = Color.YELLOW,
                            cornerRadius = dp8px,
                            borderWidth = dp1px,
                            borderColor = Color.GRAY,
                        )
                }

            tagsContainer.addView(
                tagView,
                LinearLayout
                    .LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                    ).apply {
                        marginStart = if (index == 0) 0 else dp8px.toInt()
                    },
            )
        }

        itemView.setOnClickListener(
            OnClickCooldownListener {
                listener.onTaskClick(task)
            },
        )

        checkBoxView.setOnClickListener(
            OnClickCooldownListener {
                listener.onTaskCompleteChange(task)
            },
        )

        favoriteView.setOnClickListener(
            OnClickCooldownListener {
                listener.onTaskFavoriteClick(task)
            },
        )

        tagsContainer.changeVisibility(task.customTags.isNotEmpty())
    }
}
