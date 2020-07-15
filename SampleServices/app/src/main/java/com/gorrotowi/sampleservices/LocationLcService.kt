package com.gorrotowi.sampleservices

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.MutableLiveData

class LocationLcService : LifecycleService(), LocationListener {

    private lateinit var locationManager: LocationManager
    private var destroyFrom = "SO"

    companion object {
        val liveData = MutableLiveData<String>()
    }

    override fun onCreate() {
        super.onCreate()

        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        requestLocations()
        startForeground(320, createNotification())

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
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0L, 100F, this)
            } catch (e: Exception) {
                destroyFrom = "TRYCATCH"
                e.printStackTrace()
                stopSelf()
            }
        }
    }

    override fun onLocationChanged(location: Location?) {
        Log.i("LOCATION", "${location?.toString()}")
        if (liveData.hasActiveObservers()) {
            liveData.value = location.toString()
        }
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
