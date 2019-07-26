package com.stuffshuf.workmanager

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.work.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val nm = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            nm.createNotificationChannel(
                NotificationChannel("first","defaault",
                NotificationManager.IMPORTANCE_DEFAULT)
            )
        }

//        scheduleTask()
        // for repeating task

        scheduleRepeatingTasks()

    }

    private fun scheduleRepeatingTasks() {
        val constraints=Constraints.Builder().apply {
            setRequiredNetworkType(NetworkType.NOT_REQUIRED)
            setRequiresCharging(true)
            setRequiresDeviceIdle(false)
            setRequiresStorageNotLow(true)
        }.build()

        val repeatWorkk= PeriodicWorkRequestBuilder<NetworkRequestWorker>(
            2,
            TimeUnit.MINUTES

        ).setConstraints(constraints)
            .build()

        WorkManager.getInstance(this).enqueue(repeatWorkk)

        }


    private fun scheduleTask() {
        val workerRequest = OneTimeWorkRequestBuilder<NetworkRequestWorker>()
            .setInitialDelay(1, TimeUnit.MINUTES)
            .build()

        WorkManager.getInstance(this).enqueue(workerRequest)
    }
}