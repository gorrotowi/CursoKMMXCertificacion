package com.gorrotowi.cert104

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnNotify?.setOnClickListener {
            val workerNotication = OneTimeWorkRequestBuilder<WorkerNotification>()
                .setInitialDelay(3000, TimeUnit.MILLISECONDS)
                .setConstraints(getConstrains())
                .build()
            WorkManager.getInstance(this)
                .enqueue(workerNotication)

            WorkManager.getInstance(this).getWorkInfoByIdLiveData(workerNotication.id)
                .observe(this, Observer { info ->
                    info.state.isFinished
                    info.runAttemptCount
                    info.outputData

                })
        }
    }

    private fun getConstrains(): Constraints = Constraints.Builder().apply {
        setRequiresBatteryNotLow(true)
        setRequiredNetworkType(NetworkType.CONNECTED)
        setRequiresDeviceIdle(false)
    }.build()
}