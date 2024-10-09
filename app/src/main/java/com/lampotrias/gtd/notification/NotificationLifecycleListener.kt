package com.lampotrias.gtd.notification

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.lampotrias.gtd.lifecycle.ActivityLifecycleDelegate

class NotificationLifecycleListener(
    private val notificationSystemHelper: NotificationSystemHelper,
) : ActivityLifecycleDelegate.Listener {
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)

        val activity = owner as? ComponentActivity ?: return

        requestPermissionLauncher =
            activity.registerForActivityResult(
                ActivityResultContracts.RequestPermission(),
            ) { isGranted: Boolean ->
                if (isGranted) {
                    notificationSystemHelper.createNotificationChannel()
                } else {
                    Toast.makeText(activity, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
    }

    override fun onResume(
        owner: LifecycleOwner,
        isFirstCall: Boolean,
    ) {
        super.onResume(owner, isFirstCall)

        val activity = owner as? ComponentActivity ?: return

        if (isFirstCall) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (
                    ContextCompat.checkSelfPermission(
                        activity,
                        Manifest.permission.POST_NOTIFICATIONS,
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    notificationSystemHelper.createNotificationChannel()
                } else {
                    requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
            } else {
                notificationSystemHelper.createNotificationChannel()
            }
        }
    }
}
