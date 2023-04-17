package com.ulyanenko.servicestest

import android.app.Service
import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log
import kotlinx.coroutines.*


class MyJobService : JobService() {

    private val scope = CoroutineScope(Dispatchers.Main)

    override fun onCreate() {
        super.onCreate()
        log("onCreate")
    }


    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
        log("onDestroy")
    }


    override fun onStartJob(params: JobParameters?): Boolean {
        log("onStartCommand")
        scope.launch {
            delay(1000)
            for (i in 0 until 100) {
                log("timer $i")
            }
            jobFinished(params, true)
        }
        return true
    }

    override fun onStopJob(params: JobParameters?): Boolean {
       log("onStopJob")
        return false
    }


    private fun log(msg: String) {
        Log.d("SERVICE_TAG", "MyJobService: $msg")
    }




}