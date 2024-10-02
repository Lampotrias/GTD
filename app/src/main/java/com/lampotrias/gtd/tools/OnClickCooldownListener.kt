package com.lampotrias.gtd.tools

import android.os.SystemClock
import android.view.View

open class OnClickCooldownListener(
    private val cooldown: Int = 300,
    private val onClickListener: View.OnClickListener,
) : View.OnClickListener {
    private var lastClickTime: Long = 0

    override fun onClick(v: View?) {
        val currentTime = SystemClock.elapsedRealtime()
        if (currentTime - lastClickTime >= cooldown) {
            lastClickTime = currentTime
            onClickListener.onClick(v) // Выполняем исходный onClickListener
        }
    }
}
