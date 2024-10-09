package com.lampotrias.gtd.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import com.lampotrias.gtd.R

class NotificationSystemHelper(
    private val context: Context,
) {
    private val notificationManager = context.getSystemService(NotificationManager::class.java)

    fun createNotificationChannel() {
        createNotificationChannel2()
        if (notificationManager.getNotificationChannel(CHANNEL_ID) != null) {
            return
        }

        val channel =
            NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH,
            )

        notificationManager.createNotificationChannel(channel)
    }

    private fun createNotificationChannel2() {
        if (notificationManager.getNotificationChannel(CHANNEL_ID2) != null) {
            return
        }

        val channel =
            NotificationChannel(
                CHANNEL_ID2,
                CHANNEL_NAME2,
                NotificationManager.IMPORTANCE_HIGH,
            )

        notificationManager.createNotificationChannel(channel)
    }

    fun sendNotification(
        title: String,
        message: String,
    ) {
        val notificationBuilder =
            NotificationCompat
                .Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_someday)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)

        notificationManager.notify(0, notificationBuilder.build())
    }

    companion object {
        const val CHANNEL_ID = "task_notification_channel"
        const val CHANNEL_NAME = "Уведомления о задачах"

        const val CHANNEL_ID2 = "task_notification_test"
        const val CHANNEL_NAME2 = "test"
    }
}
