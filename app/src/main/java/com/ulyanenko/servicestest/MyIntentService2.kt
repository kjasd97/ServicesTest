package com.ulyanenko.servicestest

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import kotlinx.coroutines.*


class MyIntentService2 : IntentService(NAME) {


    override fun onCreate() {
        super.onCreate()
        log("onCreate")
        setIntentRedelivery(true)
    }


    override fun onHandleIntent(intent: Intent?) {
        log("onHandleIntent")
        val page = intent?.getIntExtra(PAGE, 0) ?:0

        for (i in 0 until 5) {
            Thread.sleep(1000)
            log("timer $i $page")
        }
}




    override fun onDestroy() {
        super.onDestroy()
        log("onDestroy")
    }


    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }




    private fun log(msg: String) {
        Log.d("SERVICE_TAG", "MyForegroundService: $msg")
    }



    companion object {

        private const val EXTRA_START = "start"
        private const val PAGE = "page"

        fun newIntent(context: Context, page:Int): Intent {
            return Intent(context, MyIntentService2::class.java).apply {
                putExtra(PAGE, page)
            }
        }

        private const val NAME ="MyIntentService"
    }



}