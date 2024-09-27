package com.lampotrias.gtd.tools

import android.content.Context
import android.util.DisplayMetrics
import android.view.View

fun Context.dpToPx(number: Float): Float =
    number * resources.displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT

fun Context.dpToPx(number: Int): Int = dpToPx(number.toFloat()).toInt()

fun View.changeVisibility(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}