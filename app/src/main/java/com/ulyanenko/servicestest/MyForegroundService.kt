package com.ulyanenko.servicestest

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import kotlinx.coroutines.*


class MyForegroundService : Service() {

    private val scope = CoroutineScope(Dispatchers.Main)

    override fun onCreate() {
        super.onCreate()
        log("onCreate")
        createNotificationChanel()
        startForeground(1, createNotification())
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        log("onStartCommand")
        scope.launch {
            delay(1000)
            for (i in 0 until 100) {
                log("timer $i")
            }
        }
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
        log("onDestroy")
    }

    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }


    private fun log(msg: String) {
        Log.d("SERVICE_TAG", "MyForegroundService: $msg")
    }

    private fun createNotificationChanel(){
        val notificationManager = getSystemService(NOTIFICATION_SERVICE)
                as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    private fun createNotification():Notification{
       return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Title MyFGS")
            .setContentText("Title MyFGS")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .build()
    }

    companion object {

        private const val EXTRA_START = "start"

        fun newIntent(context: Context): Intent {
            return Intent(context, MyForegroundService::class.java)
        }

        private const val CHANNEL_ID = "channel_id"
        private const val CHANNEL_NAME = "channel_name"
    }



}