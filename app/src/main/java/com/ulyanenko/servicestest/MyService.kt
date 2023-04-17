package com.ulyanenko.servicestest

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log
import kotlinx.coroutines.*


class MyService : Service() {

    private val scope = CoroutineScope(Dispatchers.Main)

    override fun onCreate() {
        super.onCreate()
        log("onCreate")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        val start = intent?.getIntExtra(EXTRA_START, 0) ?: 0

        log("onStartCommand")
        scope.launch {
            delay(1000)
            for (i in 0 until 100) {
                log("timer $i")
            }
        }
        return START_REDELIVER_INTENT
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
        Log.d("SERVICE_TAG", "MyService: $msg")
    }

    companion object {
        private const val EXTRA_START = "start"

        fun newIntent(context: Context, start: Int): Intent {
            return Intent(context, MyService::class.java).apply {
                putExtra(EXTRA_START, start)
            }

        }
    }



}