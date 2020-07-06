package com.gorrotowi.cert104

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class WorkerNotification(context: Context, parameters: WorkerParameters) :
    Worker(context, parameters) {

    override fun doWork(): Result {
        applicationContext.createNotification()
        return Result.success()
    }

}