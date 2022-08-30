package com.example.week1app

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        Log.d(TAG,"Receiving Notification")
        super.onMessageReceived(message)

        val title = message.notification?.title ?: "No Title Found"
        val description = message.notification?.body ?: "No Message Found"

        val notification = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setContentText(description)
            .setContentTitle(title)
            .setSmallIcon(R.drawable.ic_launcher_background).apply {
                priority = NotificationCompat.PRIORITY_HIGH
            }
            .build()

        val notificationChannel = NotificationChannel(CHANNEL_ID, "FirebaseChannel",
            NotificationManager.IMPORTANCE_HIGH
        )

        (applicationContext.getSystemService(NOTIFICATION_SERVICE) as NotificationManager).apply{
            createNotificationChannel(notificationChannel)

            notify(notId, notification)
        }
    }
    companion object{
        private const val CHANNEL_ID = "FIREBASE_CHANNEL"
        private const val notId = 432
    }
}
