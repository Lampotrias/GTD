package com.lampotrias.gtd.ui

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.setPadding
import com.lampotrias.gtd.tools.dpToPx

class TagView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : AppCompatTextView(context, attrs, defStyle) {
    init {
        setPadding(context.dpToPx(4))
        setBackgroundColor(Color.YELLOW)
    }
}