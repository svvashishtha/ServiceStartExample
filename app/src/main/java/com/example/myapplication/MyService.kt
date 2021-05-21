package com.example.myapplication

import android.app.*
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.telephony.ServiceState
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat


class MyService : Service() {
    companion object {
        val START_SERVICE = "start"
        val STOP_SERVICE = "stop"
        val FOREGROUND_SERVICE = "foreground"
    }

    val CHANNEL_ID: String = "channelId"
    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val intentAction = intent?.action
        when (intentAction) {
            START_SERVICE -> {
                showToast("Service started")
            }
            STOP_SERVICE -> stopService()
            FOREGROUND_SERVICE -> doForegroundThings()
            else -> {
                showToast(intentAction ?: "Empty action intent")
            }
        }


        return super.onStartCommand(intent, flags, startId)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun doForegroundThings() {
        showToast("Going foreground")
        createNotificationChannel()
        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0)

        var builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_ice_foreground)
            .setContentTitle("My notification title")
            .setContentText("textContent")
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        val notification = builder.build()
        with(NotificationManagerCompat.from(this)) {
            // notificationId is a unique int for each notification that you must define
            notify(4, notification)
        }

// Notification ID cannot be 0.

        startForeground(4, notification)
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = resources.getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)

        }
    }

    private fun stopService() {
        showToast("Service stopping")
        try {

            stopForeground(true)
            stopSelf()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}