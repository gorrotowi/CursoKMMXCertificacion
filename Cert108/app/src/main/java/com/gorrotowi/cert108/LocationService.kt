package com.gorrotowi.cert108

import android.Manifest
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.*
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat

class LocationService : Service(), LocationListener {

    private lateinit var locationManager: LocationManager

    private lateinit var messenger: Messenger

    override fun onCreate() {
        super.onCreate()
        Log.wtf("SERVICE", "OnCreate")
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        requestLocations()
        startForeground(422, createNotification())
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.e("OnStartCommand", "----->>>><<<<<-----")
        Log.e("Intent", "****----****")
        intent?.extras?.let { bundle ->
            Log.i("-", "${bundle.keySet()?.map { it }}")
            Log.i("-", "${bundle.getString("NAME")}")
            Log.i("-", "${bundle.getString("AGE")}")
            Log.i("-", "${bundle.getBoolean("OTHER")}")
        }
        return START_STICKY
    }


    override fun onBind(intent: Intent?): IBinder? {
        messenger = Messenger(IncommingHandler(this))
        return messenger.binder
    }

    class IncommingHandler(val context: Context) : Handler() {
        companion object {
            val MESSAGE_HELLO = 20
            val MESSAGE_UPADTE = 40
            val MESSAGE_DELETE = 60
        }

        override fun handleMessage(msg: Message) {
            when (msg.what) {
                MESSAGE_HELLO -> createToast("Hello from Handler")
                MESSAGE_UPADTE -> createToast("UPDATE from Handler")
                MESSAGE_DELETE -> createToast("DELETE from Handler")
                else -> createToast("Unknow from Handler")
            }
        }

        private fun createToast(stringMessage: String) {
            Toast.makeText(context.applicationContext, stringMessage, Toast.LENGTH_SHORT).show()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("SERVICE--->", "onDestroy")
        locationManager.removeUpdates(this)
    }

    private fun requestLocations() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Log.e("", "requestLocations: Not Granted")
            Toast.makeText(this, "Aprueba los permisos de localización", Toast.LENGTH_SHORT).show()
            stopSelf()
        } else {
            try {
                locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    1000L,
                    10F,
                    this
                )
            } catch (e: Exception) {
                e.printStackTrace()
                stopSelf()
            }
        }
    }

    override fun onLocationChanged(location: Location?) {
        Log.i("LocationService", "${location.toString()}")
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
        Log.d(
            "STATUSCHANGED", """
                Provider $provider
                Status $status
                BundleExtras ${extras?.keySet()?.map { it }}
            """.trimIndent()
        )
    }

    override fun onProviderEnabled(provider: String?) {
        Log.w("onProviderEnabled", "$provider")
    }

    override fun onProviderDisabled(provider: String?) {
        Log.wtf("onProviderDisabled", "$provider")
    }
}