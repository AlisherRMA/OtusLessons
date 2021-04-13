package ru.otus.otushometask1.domain

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import ru.otus.otushometask1.R
import ru.otus.otushometask1.data.database.entity.Film
import ru.otus.otushometask1.presentation.view.MainActivity


class NotificationPublisher : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create the NotificationChannel
            val name = "Alarme"
            val descriptionText = "Detalhes do Alarme"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val mChannel = NotificationChannel("AlarmId", name, importance)
            mChannel.description = descriptionText
            val notificationManager =
                context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(mChannel)
        }

        val bundle = intent.getBundleExtra("filmBundle")
        val film  = bundle?.getParcelable<Film>("film") as Film

        val intent = Intent(context,MainActivity::class.java)
        intent.putExtra("filmBundle", bundle)
        intent.putExtra("tester", "test123")

        // Create the notification to be shown
        val mBuilder = NotificationCompat.Builder(context!!, "AlarmId")
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("Не забудьте посмотреть")
            .setContentText("Вы хотели посмотреть фильм ${film.title}")
            .setAutoCancel(true)
            .setContentIntent(
                PendingIntent.getActivity(
                context, // Context from onReceive method.
                0,
                    intent, // Activity you want to launch onClick.
                    PendingIntent.FLAG_UPDATE_CURRENT
            )
            )
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        // Get the Notification manager service
        val am = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Generate an Id for each notification
        val id = System.currentTimeMillis() / 1000

        // Show a notification
        am.notify(id.toInt(), mBuilder.build())
    }
}