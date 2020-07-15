package com.gorrotowi.sampleservices

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class AirPlaneBroadCastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intentReceiver: Intent) {
        Log.e("SCHEME", "Action: ${intentReceiver.action}\\n")
        Log.e("SCHEME", "URI: ${intentReceiver.toUri(Intent.URI_INTENT_SCHEME)}")
    }
}