package ru.otus.otushometask1.domain

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log

const val LOG_TAG = "myLogs"

class NotificationPublisher : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        Log.d(LOG_TAG, "onReceive")
        Log.d(LOG_TAG, "action = " + intent.action)
        Log.d(LOG_TAG, "extra = " + intent.getStringExtra("extra"))
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notification = intent.getParcelableExtra<Notification>(NOTIFICATION)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val notificationChannel = NotificationChannel(
                "10001",
                "NOTIFICATION_CHANNEL_NAME",
                importance
            )
            assert(notificationManager != null)
            notificationManager.createNotificationChannel(notificationChannel)
        }
        else {
            Log.d("as", "IT'S NOT WORKING IMPLEMENT IT")
        }
        val id = intent.getIntExtra(NOTIFICATION_ID, 0)
        assert(notificationManager != null)
        notificationManager.notify(id, notification)
    }

    companion object {
        var NOTIFICATION_ID = "notification-id"
        var NOTIFICATION = "notification"
    }
}