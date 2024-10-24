package com.lampotrias.gtd.data

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM
import com.lampotrias.gtd.notification.NotificationReceiver

class TaskAlarmManager(
    private val context: Context,
) {
    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    fun setTaskAlarm(
        taskId: Long,
        triggerTime: Long,
    ): Boolean {
        val intent =
            Intent(context, NotificationReceiver::class.java).apply {
                putExtra(TASK_ID_KEY, taskId)
            }

        val pendingIntent =
            PendingIntent.getBroadcast(
                context,
                taskId.toInt(), // Unique identifier
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
            )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (alarmManager.canScheduleExactAlarms()) {
                alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    triggerTime,
                    pendingIntent,
                )
            } else {
                context.startActivity(Intent(ACTION_REQUEST_SCHEDULE_EXACT_ALARM))
            }
        } else {
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                triggerTime,
                pendingIntent,
            )
        }

        return true
    }

    fun cancelTaskAlarm(taskId: Long) {
        val intent = Intent(context, NotificationReceiver::class.java)
        val pendingIntent =
            PendingIntent.getBroadcast(
                context,
                taskId.toInt(),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
            )

        alarmManager.cancel(pendingIntent)
    }

    fun isTaskAlarmSet(taskId: Long): Boolean {
        val intent = Intent(context, NotificationReceiver::class.java)
        val pendingIntent =
            PendingIntent.getBroadcast(
                context,
                taskId.toInt(),
                intent,
                PendingIntent.FLAG_NO_CREATE or PendingIntent.FLAG_IMMUTABLE, // Don't create a new one, just check if it exists
            )
        return pendingIntent != null
    }

    companion object {
        private const val TASK_ID_KEY = "TASK_ID"
    }
}
