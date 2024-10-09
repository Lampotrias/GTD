package com.lampotrias.gtd.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(
        context: Context?,
        intent: Intent?,
    ) {
        if (intent == null || context == null) {
            return
        }

        val notificationSystemHelper = NotificationSystemHelper(context)
        notificationSystemHelper.sendNotification(
            intent.getStringExtra("title") ?: "123123123",
            intent.getStringExtra("message") ?: "qweqweqweqweq",
        )
    }
}
