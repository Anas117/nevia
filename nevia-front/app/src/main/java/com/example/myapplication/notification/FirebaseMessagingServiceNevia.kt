package com.example.myapplication.notification

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.myapplication.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FirebaseMessagingServiceNevia : FirebaseMessagingService() {

    private val tag = "CONSOLE"
    override fun onMessageReceived(message: RemoteMessage) {
        Log.d(tag, "from : " + message.from)
        Log.d(tag, "Message de la notification : " + message.notification?.body!!)
        createNotification(message.notification?.body!!, message.notification?.title!!)
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    private fun createNotification(body: String, title: String) {
        //notifications
        val channel_ID = "channel_id_example"
        val channel_name = "channelName"
        val notification_id = 0

        val soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notification = NotificationCompat.Builder(this, channel_ID)
            .setContentText(body)
            .setContentTitle(title)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setSound(soundUri)
            .setStyle(NotificationCompat.BigTextStyle().bigText(body))
            .build()

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel(channel_ID, channel_name, NotificationManager.IMPORTANCE_DEFAULT).apply {
                lightColor = Color.GREEN
                enableLights(true)
            }
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(notification_id, notification)
        Log.i(tag, "test, send notification")
    }

    override fun onNewToken(token: String) {
        Log.d(tag, "Refreshed token : $token")
        sendRegistrationToServer(token)
    }

    private fun sendRegistrationToServer(token: String?) {
        Log.d(tag, "sendRegistrationTokenToServer($token)")
    }
}