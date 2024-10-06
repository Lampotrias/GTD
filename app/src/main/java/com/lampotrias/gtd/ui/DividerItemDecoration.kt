package com.lampotrias.gtd.ui

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.annotation.ColorInt
import androidx.recyclerview.widget.RecyclerView

class DividerItemDecoration(
    private val thickness: Float,
    @ColorInt private val color: Int,
) : RecyclerView.ItemDecoration() {
    private val paint =
        Paint().apply {
            isAntiAlias = true
            this.color = this@DividerItemDecoration.color
            strokeWidth = thickness
            style = Paint.Style.FILL_AND_STROKE
        }

    override fun onDraw(
        canvas: Canvas,
        parent: RecyclerView,
        state: RecyclerView.State,
    ) {
        super.onDraw(canvas, parent, state)
        val childCount = parent.childCount

        for (i in 0 until childCount - 1) {
            val child = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams

            val left = parent.paddingLeft.toFloat()
            val right = (parent.width - parent.paddingRight).toFloat()
            val top = (child.bottom + params.bottomMargin).toFloat()
            val bottom = top + thickness

            canvas.drawRect(left, top, right, bottom, paint)
        }
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State,
    ) {
        val position = parent.getChildAdapterPosition(view)
        val itemCount = state.itemCount

        if (position < itemCount - 1) {
            outRect.set(0, 0, 0, thickness.toInt())
        } else {
            outRect.setEmpty()
        }
    }
}
