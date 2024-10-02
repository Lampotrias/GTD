package com.lampotrias.gtd.tools

import android.graphics.Color
import android.graphics.drawable.GradientDrawable

object DrawableUtils {
    fun createCustomBackground(
        backgroundColor: Int,
        cornerRadius: Float = 0f,
        borderWidth: Int = 0,
        borderColor: Int = Color.TRANSPARENT,
    ): GradientDrawable =
        GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            setColor(backgroundColor)
            cornerRadii =
                floatArrayOf(
                    cornerRadius,
                    cornerRadius,
                    cornerRadius,
                    cornerRadius,
                    cornerRadius,
                    cornerRadius,
                    cornerRadius,
                    cornerRadius,
                )
            setStroke(borderWidth, borderColor)
        }
}
