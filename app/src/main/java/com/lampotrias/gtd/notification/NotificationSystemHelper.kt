package com.lampotrias.gtd.notification

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM
import androidx.core.app.NotificationCompat
import com.lampotrias.gtd.R

class NotificationSystemHelper(
    private val context: Context,
) {
    private val notificationManager = context.getSystemService(NotificationManager::class.java)
    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

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

    fun scheduleNotification(
        context: Context,
        requestCode: Int,
        title: String,
        message: String,
        timeInMillis: Long,
    ) {
        val intent =
            Intent(context, NotificationReceiver::class.java).apply {
                putExtra("title", title)
                putExtra("message", message)
            }

        val pendingIntent =
            PendingIntent.getBroadcast(
                context,
                requestCode,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
            )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (alarmManager.canScheduleExactAlarms()) {
                alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    timeInMillis,
                    pendingIntent,
                )
            } else {
                context.startActivity(Intent(ACTION_REQUEST_SCHEDULE_EXACT_ALARM))
            }
        } else {
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                timeInMillis,
                pendingIntent,
            )
        }
    }

//    val alarmManager: AlarmManager = context.getSystemService<AlarmManager>()!!
//    when {
//        // If permission is granted, proceed with scheduling exact alarms.
//        alarmManager.canScheduleExactAlarms() -> {
//            alarmManager.setExact(...)
//        }
//        else -> {
//            // Ask users to go to exact alarm page in system settings.
//            startActivity(Intent(ACTION_REQUEST_SCHEDULE_EXACT_ALARM))
//        }
//    }

//    override fun onResume() {
//        …
//        if (alarmManager.canScheduleExactAlarms()) {
//            // Set exact alarms.
//            alarmManager.setExact(...)
//        }
//        else {
//            // Permission not yet approved. Display user notice and revert to a fallback
//            // approach.
//            alarmManager.setWindow(...)
//        }
//    }

    companion object {
        const val CHANNEL_ID = "task_notification_channel"
        const val CHANNEL_NAME = "Уведомления о задачах"

        const val CHANNEL_ID2 = "task_notification_test"
        const val CHANNEL_NAME2 = "test"
    }
}
