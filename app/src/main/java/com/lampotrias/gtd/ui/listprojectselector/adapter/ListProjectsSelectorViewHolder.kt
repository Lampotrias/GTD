package com.lampotrias.gtd.ui.listprojectselector.adapter

import android.view.View
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.lampotrias.gtd.R
import com.lampotrias.gtd.tools.changeInvisible
import com.lampotrias.gtd.ui.listprojectselector.ListProjectAdapterModel

class ListProjectsSelectorViewHolder(
    itemView: View,
) : RecyclerView.ViewHolder(itemView) {
    private val titleView = itemView.findViewById<AppCompatTextView>(R.id.item_title)
    private val iconView = itemView.findViewById<ImageView>(R.id.item_icon)
    private val checkImageView = itemView.findViewById<ImageView>(R.id.item_check)

    fun bind(model: ListProjectAdapterModel) {
        titleView.text = model.title

        iconView.setImageDrawable(model.icon)
        iconView.setColorFilter(model.iconColor)

        checkImageView.changeInvisible(model.isSelected)
    }
}

