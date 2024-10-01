package com.lampotrias.gtd.ui.listprojectselector

import android.graphics.drawable.Drawable
import androidx.annotation.ColorInt

data class ListProjectAdapterModel(
    val id: Long = 0,
    val code: String = "",
    val icon: Drawable?,
    @ColorInt
    val iconColor: Int,
    val title: String,
    val isSelected: Boolean,
)
