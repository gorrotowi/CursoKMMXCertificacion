package com.gorrotowi.sampleservices

import android.Manifest
import android.app.IntentService
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
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

const val EVENT_LOCATION = "com.gorro.services.location.event"

class SampleService : IntentService("LocationService"), LocationListener, CoroutineScope {

    private lateinit var messenger: Messenger

    private lateinit var locationManager: LocationManager
    private var destroyFrom = "SO"

    companion object {
        val liveData = MutableLiveData<String>()
    }

    override fun onCreate() {
        super.onCreate()
        Log.e("OnCreateService", "create service")
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        requestLocations()
        startForeground(320, createNotification())
        scope.launch {
            Log.i("COROUTINE", "START")
            delay(1000)
            Log.i("COROUTINE", "END")
        }
    }

    private fun requestLocations() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Log.e("", "requestLocations: Not Granted")
            Toast.makeText(this, "Aprueba los permisos de localización", Toast.LENGTH_SHORT).show()
            destroyFrom = "NOT GRANTED PERMISSIONS STOPSELF"
            stopSelf()
        } else {
            try {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0L, 10F, this)
            } catch (e: Exception) {
                destroyFrom = "TRYCATCH"
                e.printStackTrace()
                stopSelf()
            }
        }
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

        return START_NOT_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("ONDESTROY", destroyFrom)
        scope.cancel()
        locationManager.removeUpdates(this)
    }

    override fun onBind(intent: Intent?): IBinder? {
        messenger = Messenger(IncomingHandler(this))
        return messenger.binder
    }

    override fun onHandleIntent(intent: Intent?) {
        TODO("Not yet implemented")
    }

    override fun onLocationChanged(location: Location?) {
        Log.i("LOCATION", "${location?.toString()}")
        if (LocationLcService.liveData.hasActiveObservers()) {
            LocationLcService.liveData.value = location.toString()
        }
        val eventLocation = Intent(EVENT_LOCATION).apply {
            putExtra("LOCATION", location.toString())
        }
        sendBroadcast(eventLocation)
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

    inner class IncomingHandler(
        context: Context,
        private val applicationContext: Context = context.applicationContext
    ) : Handler() {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                20 -> {
                    Toast.makeText(applicationContext, "MESSAGE HELLO", Toast.LENGTH_SHORT).show()
                }
                else -> Toast.makeText(applicationContext, "MESSAGE UNKNOW", Toast.LENGTH_SHORT)
                    .show()
            }

        }
    }

    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() {
            return job + Dispatchers.Default
        }
    private val scope = CoroutineScope(coroutineContext)
}