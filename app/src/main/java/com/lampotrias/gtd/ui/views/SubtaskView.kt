package com.lampotrias.gtd.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TextView
import com.lampotrias.gtd.domain.model.TaskDomainModel
import com.lampotrias.gtd.tools.OnClickCooldownListener
import com.lampotrias.gtd.tools.dpToPx

class SubtaskView
    @JvmOverloads
    constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyle: Int = 0,
    ) : LinearLayout(context, attrs, defStyle) {
        private val padding = context.dpToPx(4)
        private val checkbox = CheckBox(context)
        private val taskNameView =
            TextView(context).apply {
            }

        var listener: Listener? = null

        init {
            orientation = HORIZONTAL
            setPadding(0, padding, padding, padding)
            gravity = Gravity.CENTER_VERTICAL

            addView(
                checkbox,
                LayoutParams(WRAP_CONTENT, WRAP_CONTENT).apply {
//                    marginStart = context.dpToPx(10)
                },
            )

            addView(
                taskNameView,
                LayoutParams(WRAP_CONTENT, WRAP_CONTENT).apply {
                    marginStart = context.dpToPx(10)
                },
            )
        }

        fun applySubtask(subtask: TaskDomainModel) {
            with(checkbox) {
                isChecked = subtask.isCompleted

                setOnCheckedChangeListener { _, isChecked ->
                    listener?.onSubtaskCompleteChange(subtask, isChecked)
                }
            }

            taskNameView.text = subtask.name

            setOnClickListener(
                OnClickCooldownListener {
                    listener?.onSubtaskClicked(subtask)
                },
            )
        }

        interface Listener {
            fun onSubtaskClicked(subtask: TaskDomainModel)

            fun onSubtaskCompleteChange(
                subtask: TaskDomainModel,
                isChecked: Boolean,
            )
        }
    }
