package com.ulyanenko.servicestest

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.JobIntentService
import androidx.core.app.NotificationCompat
import kotlinx.coroutines.*


class MyJobIntentService : JobIntentService() {


    override fun onCreate() {
        super.onCreate()
        log("onCreate")

    }



    override fun onDestroy() {
        super.onDestroy()
        log("onDestroy")
    }

    override fun onHandleWork(intent: Intent) {
        log("onHandleIntent")
        val page = intent.getIntExtra(PAGE, 0) ?:0

        for (i in 0 until 5) {
            Thread.sleep(1000)
            log("timer $i $page")
        }
    }





    private fun log(msg: String) {
        Log.d("SERVICE_TAG", "MyJobIntentService: $msg")
    }



    companion object {

        private const val EXTRA_START = "start"
        private const val PAGE = "page"

        fun enqueue (context:Context, page:Int){
           enqueueWork(
                context,
                MyJobIntentService::class.java,
                1,
                newIntent(context, page)
            )
        }

       private fun newIntent(context: Context, page:Int): Intent {
            return Intent(context, MyJobIntentService::class.java).apply {
                putExtra(PAGE, page)
            }
        }
    }



}