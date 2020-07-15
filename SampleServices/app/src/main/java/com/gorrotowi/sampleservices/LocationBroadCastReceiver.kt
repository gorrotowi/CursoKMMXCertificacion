package com.gorrotowi.sampleservices

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.MutableLiveData

class LocationBroadCastReceiver : BroadcastReceiver() {

    val broadcastLocation = MutableLiveData<String>()

    override fun onReceive(context: Context?, intent: Intent?) {
        intent?.extras?.let { bundle ->
            val location = bundle.getString("LOCATION")
            Log.i("BROADCAST", "_____----_____")
            Log.i("BROADCAST", "$location")
            if (broadcastLocation.hasActiveObservers()) {
                broadcastLocation.value = location
            }
        }
    }
}